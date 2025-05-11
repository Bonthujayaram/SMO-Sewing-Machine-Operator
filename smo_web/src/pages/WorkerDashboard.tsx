import { useNavigate } from 'react-router-dom';
import { User } from 'lucide-react';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

export function WorkerDashboard() {
  const navigate = useNavigate();

  const { data: workers, isLoading, error } = useQuery({
    queryKey: ['workers'],
    queryFn: async () => {
      const response = await axios.get('http://20.30.65.174:8080/worker-names');
      return response.data; // List of worker names
    },
  });

  if (isLoading) return <p>Loading workers...</p>;
  if (error) return <p>Error fetching workers.</p>;

  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-8">Worker Performance Dashboard</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {workers?.map((worker: string) => (
          <button
            key={worker}
            onClick={() => navigate(`/worker/${worker}`)} // Use name as identifier
            className="p-8 rounded-lg transition-all transform hover:scale-105 flex flex-col items-center justify-center bg-blue-500 text-white hover:bg-blue-600 shadow-lg"
          >
            <User size={48} className="mb-4" />
            <span className="text-xl font-semibold">{worker}</span>
          </button>
        ))}
      </div>
    </div>
  );
}
