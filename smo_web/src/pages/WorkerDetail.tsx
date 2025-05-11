import { useParams } from 'react-router-dom';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, LineChart, Line } from 'recharts';

const workersData = {
  '1': {
    name: 'Worker 1',
    performance: [
      { name: 'Jan', efficiency: 85, productivity: 90, quality: 88 },
      { name: 'Feb', efficiency: 87, productivity: 88, quality: 89 },
      { name: 'Mar', efficiency: 89, productivity: 92, quality: 90 }
    ],
    candlestick: [
      { date: '2024-01', open: 85, close: 88, high: 90, low: 82 },
      { date: '2024-02', open: 88, close: 85, high: 92, low: 78 },
      { date: '2024-03', open: 85, close: 90, high: 92, low: 85 }
    ]
  },
  '2': {
    name: 'Worker 2',
    performance: [
      { name: 'Jan', efficiency: 78, productivity: 82, quality: 85 },
      { name: 'Feb', efficiency: 80, productivity: 83, quality: 86 },
      { name: 'Mar', efficiency: 82, productivity: 85, quality: 87 }
    ],
    candlestick: [
      { date: '2024-01', open: 80, close: 83, high: 85, low: 78 },
      { date: '2024-02', open: 83, close: 81, high: 86, low: 79 },
      { date: '2024-03', open: 81, close: 85, high: 87, low: 80 }
    ]
  },
  '3': {
    name: 'Worker 3',
    performance: [
      { name: 'Jan', efficiency: 92, productivity: 88, quality: 90 },
      { name: 'Feb', efficiency: 90, productivity: 89, quality: 91 },
      { name: 'Mar', efficiency: 91, productivity: 90, quality: 92 }
    ],
    candlestick: [
      { date: '2024-01', open: 88, close: 90, high: 92, low: 86 },
      { date: '2024-02', open: 90, close: 89, high: 93, low: 87 },
      { date: '2024-03', open: 89, close: 92, high: 94, low: 88 }
    ]
  },
  '4': {
    name: 'Worker 4',
    performance: [
      { name: 'Jan', efficiency: 88, productivity: 85, quality: 87 },
      { name: 'Feb', efficiency: 86, productivity: 87, quality: 88 },
      { name: 'Mar', efficiency: 89, productivity: 88, quality: 89 }
    ],
    candlestick: [
      { date: '2024-01', open: 85, close: 87, high: 89, low: 83 },
      { date: '2024-02', open: 87, close: 86, high: 90, low: 84 },
      { date: '2024-03', open: 86, close: 89, high: 91, low: 85 }
    ]
  }
};

export function WorkerDetail() {
  const { id } = useParams<{ id: string }>();
  const workerData = id ? workersData[id] : null;

  if (!workerData) {
    return (
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Worker not found</h1>
      </div>
    );
  }

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-8">{workerData.name} Performance</h1>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Efficiency Metrics</h2>
          <BarChart width={500} height={300} data={workerData.performance}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="efficiency" fill="#4F46E5" />
          </BarChart>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Productivity Metrics</h2>
          <BarChart width={500} height={300} data={workerData.performance}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="productivity" fill="#10B981" />
          </BarChart>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Quality Metrics</h2>
          <BarChart width={500} height={300} data={workerData.performance}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="quality" fill="#F59E0B" />
          </BarChart>
        </div>
        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-xl font-semibold mb-6">Performance Trend</h2>
          <LineChart width={500} height={300} data={workerData.candlestick}>
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