import React from 'react';
import { useQuery } from '@tanstack/react-query';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  ResponsiveContainer,
  CartesianGrid,
} from 'recharts';

interface TimeRange {
  startTime: number;
  duration: number;
}

interface MachineData {
  machine: string;
  timeRanges: TimeRange[];
}

// Fetch data function
const fetchMachineData = async (): Promise<MachineData[]> => {
  const response = await fetch('http://20.30.65.174:8080/api/machine-activities');
  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }
  return response.json();
};

// Convert decimal hours to HH:mm (e.g., 10.5 → 10:30)
const formatTime = (decimalHours: number) => {
  const hours = Math.floor(decimalHours);
  const minutes = Math.round((decimalHours % 1) * 60);
  return `${hours}:${minutes.toString().padStart(2, '0')}`;
};

// Convert hours → minutes
const hoursToMinutes = (hours: number) => hours * 60;

/** Invisible bar shape for the 'start' offset bar, so there's no big hover area. */
function InvisibleOffsetBar() {
  return null;
}

export function BundleDashboard() {
  const { data: timelineData, error, isLoading } = useQuery({
    queryKey: ['machineActivities'],
    queryFn: fetchMachineData,
  });

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error fetching data</div>;

  // 1) Find global earliest start time (for offset)
  const minTime = Math.min(
    ...timelineData!.flatMap(machine =>
      machine.timeRanges.map(range => hoursToMinutes(range.startTime))
    )
  );

  // 2) Build one data object per machine, sorted by startTime
  const chartData = timelineData!.map(machine => {
    const sortedRanges = machine.timeRanges.slice().sort((a, b) => a.startTime - b.startTime);
    return sortedRanges.reduce((acc, range, i) => {
      acc[`start_${i}`] = hoursToMinutes(range.startTime) - minTime;
      acc[`duration_${i}`] = hoursToMinutes(range.duration);
      return acc;
    }, { machine: machine.machine } as Record<string, any>);
  });

  // 3) Maximum offset for X-axis domain
  const maxTime = Math.max(
    ...timelineData!.flatMap(machine =>
      machine.timeRanges.map(range =>
        hoursToMinutes(range.startTime + range.duration) - minTime
      )
    )
  );

  // 4) Find the max number of time ranges for any machine
  const maxRanges = Math.max(...timelineData!.map(m => m.timeRanges.length));

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-6">Machine Activity Timeline</h1>
      <div className="bg-white p-6 rounded-lg shadow">
        <ResponsiveContainer width="100%" height={400}>
          <BarChart layout="vertical" data={chartData} margin={{ top: 5, right: 30, left: 60, bottom: 20 }} barSize={20}>
            <CartesianGrid strokeDasharray="3 3" horizontal={false} />

            <XAxis
              type="number"
              domain={[0, maxTime]}
              tickFormatter={(val) => formatTime((val + minTime) / 60)}
            />
            <YAxis type="category" dataKey="machine" width={80} />

            {Array.from({ length: maxRanges }, (_, i) => (
              <React.Fragment key={i}>
                <Bar dataKey={`start_${i}`} stackId={`range_${i}`} fill="transparent" shape={<InvisibleOffsetBar />} />
                <Bar
                  dataKey={`duration_${i}`}
                  stackId={`range_${i}`}
                  fill="#FFA500"
                  radius={[4, 4, 4, 4]}
                  label={{
                    content: (props) => {
                      const { x, y, width, height } = props;
                      if (!x || !y || !width || !height) return null;
                      const cx = x + width / 2;
                      const cy = y + height / 2;
                      return (
                        <text x={cx} y={cy} textAnchor="middle" dominantBaseline="middle" fill="#fff" fontSize={12}>
                          {`Machine ${i + 1}`}
                        </text>
                      );
                    },
                  }}
                />
              </React.Fragment>
            ))}
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}
