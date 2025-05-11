import { useParams } from 'react-router-dom';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, LineChart, Line } from 'recharts';

const machinesData = {
  '1': {
    name: 'Machine 1',
    performance: [
      { name: 'Jan', efficiency: 92, utilization: 88, downtime: 5 },
      { name: 'Feb', efficiency: 90, utilization: 89, downtime: 4 },
      { name: 'Mar', efficiency: 91, utilization: 90, downtime: 3 }
    ],
    candlestick: [
      { date: '2024-01', open: 88, close: 90, high: 92, low: 86 },
      { date: '2024-02', open: 90, close: 89, high: 93, low: 87 },
      { date: '2024-03', open: 89, close: 91, high: 94, low: 88 }
    ],
    worker: { name: 'John Doe', shift: 'Morning', experience: '5 years' }
  },
  '2': {
    name: 'Machine 2',
    performance: [
      { name: 'Jan', efficiency: 85, utilization: 82, downtime: 8 },
      { name: 'Feb', efficiency: 86, utilization: 83, downtime: 7 },
      { name: 'Mar', efficiency: 87, utilization: 84, downtime: 6 }
    ],
    candlestick: [
      { date: '2024-01', open: 82, close: 85, high: 87, low: 80 },
      { date: '2024-02', open: 85, close: 83, high: 88, low: 81 },
      { date: '2024-03', open: 83, close: 86, high: 89, low: 82 }
    ],
    worker: { name: 'Jane Smith', shift: 'Morning', experience: '3 years' }
  },
  '3': {
    name: 'Machine 3',
    performance: [
      { name: 'Jan', efficiency: 78, utilization: 75, downtime: 12 },
      { name: 'Feb', efficiency: 80, utilization: 77, downtime: 10 },
      { name: 'Mar', efficiency: 82, utilization: 79, downtime: 9 }
    ],
    candlestick: [
      { date: '2024-01', open: 75, close: 78, high: 80, low: 73 },
      { date: '2024-02', open: 78, close: 77, high: 81, low: 74 },
      { date: '2024-03', open: 77, close: 80, high: 82, low: 75 }
    ],
    worker: { name: 'Mike Johnson', shift: 'Afternoon', experience: '4 years' }
  }
};

export function MachineDetail() {
  const { id } = useParams<{ id: string }>();
  const machineData = id ? machinesData[id] : null;

  if (!machineData) {
    return (
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Machine not found</h1>
      </div>
    );
  }

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-8">{machineData.name} Performance</h1>
      
      <div className="bg-white rounded-lg shadow-lg mb-8">
        <div className="p-6">
          <h2 className="text-xl font-semibold mb-6">Current Worker Assignment</h2>
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Worker</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Shift</th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Experience</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                <tr>
                  <td className="px-6 py-4 whitespace-nowrap">{machineData.worker.name}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{machineData.worker.shift}</td>
                  <td className="px-6 py-4 whitespace-nowrap">{machineData.worker.experience}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Efficiency Metrics</h2>
          <BarChart width={500} height={300} data={machineData.performance}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="efficiency" fill="#10B981" />
          </BarChart>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Utilization Metrics</h2>
          <BarChart width={500} height={300} data={machineData.performance}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="utilization" fill="#3B82F6" />
          </BarChart>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Downtime Metrics</h2>
          <BarChart width={500} height={300} data={machineData.performance}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="downtime" fill="#EF4444" />
          </BarChart>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Performance Trend</h2>
          <LineChart width={500} height={300} data={machineData.candlestick}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="date" />
            <YAxis domain={['dataMin - 5', 'dataMax + 5']} />
            <Tooltip
              formatter={(value: any, name: string) => [value, name.charAt(0).toUpperCase() + name.slice(1)]}
            />
            <Legend />
            <Line
              type="monotone"
              dataKey="high"
              stroke="#10B981"
              dot={false}
              name="High"
            />
            <Line
              type="monotone"
              dataKey="low"
              stroke="#EF4444"
              dot={false}
              name="Low"
            />
            <Line
              type="monotone"
              dataKey="open"
              stroke="#3B82F6"
              dot={true}
              name="Open"
            />
            <Line
              type="monotone"
              dataKey="close"
              stroke="#8B5CF6"
              dot={true}
              name="Close"
            />
          </LineChart>
        </div>
      </div>
    </div>
  );
}