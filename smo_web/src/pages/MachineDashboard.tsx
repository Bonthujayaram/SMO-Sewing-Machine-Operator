import { useNavigate } from 'react-router-dom';
import { Settings } from 'lucide-react';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

export function MachineDashboard() {
  const navigate = useNavigate();

  const { data: machines, isLoading, error } = useQuery({
    queryKey: ['machines'],
    queryFn: async () => {
      const response = await axios.get('http://20.30.65.174:8080/machines');
      return response.data.map((machine: { machinename: string }) => machine.machinename); // Extract only names
    },
  });

  if (isLoading) return <p>Loading machines...</p>;
  if (error) return <p>Error fetching machines.</p>;

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-8">Machine Dashboard</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
        {machines?.map((machine: string) => (
          <button
            key={machine}
            onClick={() => navigate(`/machine/${machine}`)} // Use name as identifier
            className="p-8 rounded-lg transition-all transform hover:scale-105 flex flex-col items-center justify-center bg-emerald-500 text-white hover:bg-emerald-600 shadow-lg"
          >
            <Settings size={48} className="mb-4" />
            <span className="text-xl font-semibold">{machine}</span>
          </button>
        ))}
      </div>
    </div>
  );
}
