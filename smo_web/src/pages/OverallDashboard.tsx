import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  ResponsiveContainer,
  CartesianGrid
} from 'recharts';

const API_URL = "http://20.30.65.174:8080/worker/Jane%20Smith/performance";

// Convert timestamp to total minutes from midnight
const getMinutesFromMidnight = (timestamp: string) => {
  const date = new Date(timestamp);
  return date.getHours() * 60 + date.getMinutes();
};

// Convert minutes to HH:mm format for display
const formatTime = (minutes: number) => {
  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return `${hours}:${mins.toString().padStart(2, '0')}`;
};

// Generate tick values for every 10 minutes
const timeTicks = Array.from({ length: 145 }, (_, i) => i * 10);

export function OverallDashboard() {
  const [timelineData, setTimelineData] = useState([]);

  useEffect(() => {
    axios.get(API_URL)
      .then(response => {
        const rawData = response.data.map((entry: any) => ({
          machine: `Machine ${entry.machineid}`,
          startTime: getMinutesFromMidnight(entry.inscan),
          duration: getMinutesFromMidnight(entry.outscan) - getMinutesFromMidnight(entry.inscan)
        }));

        // Ensure unique machine labels but maintain multiple stacked bars
        const uniqueMachines = new Set();
        const processedData = rawData.map(entry => {
          if (uniqueMachines.has(entry.machine)) {
            return { ...entry, machine: '' }; // Empty label for duplicate rows (stacking)
          }
          uniqueMachines.add(entry.machine);
          return entry;
        });

        setTimelineData(processedData);
        console.log("Processed Chart Data:", processedData);
      })
      .catch(error => console.error("Error fetching data:", error));
  }, []);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Machine Activity Timeline (1 Day)</h1>

      {/* Scrollable Wrapper */}
      <div className="bg-white p-6 rounded-lg shadow overflow-x-auto">
        {/* Set fixed width to allow scrolling on small screens */}
        <div style={{ width: "1200px", minWidth: "100%" }}>
          <ResponsiveContainer width="100%" height={400}>
            <BarChart
              layout="vertical"
              data={timelineData}
              margin={{ top: 5, right: 30, left: 60, bottom: 20 }}
              barSize={20}
            >
              <CartesianGrid strokeDasharray="3 3" horizontal={false} />
              <XAxis
                type="number"
                domain={[0, 1440]}
                tickFormatter={formatTime}
                ticks={timeTicks}
              />
              <YAxis type="category" dataKey="machine" width={100} />

              {/* Offset Bar (invisible) */}
              <Bar dataKey="startTime" stackId="range" fill="transparent" />
              {/* Actual Activity Duration */}
              <Bar dataKey="duration" stackId="range" fill="#FFA500" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}
