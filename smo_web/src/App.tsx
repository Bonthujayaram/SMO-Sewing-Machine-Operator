import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { Sidebar } from './components/Sidebar';
import { WorkerDashboard } from './pages/WorkerDashboard';
import { MachineDashboard } from './pages/MachineDashboard';
import { BundleDashboard } from './pages/BundleDashboard';
import { OverallDashboard } from './pages/OverallDashboard';
import { WorkerDetail } from './pages/WorkerDetail';
import { MachineDetail } from './pages/MachineDetail';

const queryClient = new QueryClient(); // Create a new instance of QueryClient

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <div className="min-h-screen bg-gray-100">
          <Sidebar />
          <main className="md:ml-64 min-h-screen">
            <div className="pt-16 md:pt-0">
              <Routes>
                <Route path="/" element={<WorkerDashboard />} />
                <Route path="/worker/:id" element={<WorkerDetail />} />
                <Route path="/machine" element={<MachineDashboard />} />
                <Route path="/machine/:id" element={<MachineDetail />} />
                <Route path="/bundle" element={<BundleDashboard />} />
                <Route path="/overall" element={<OverallDashboard />} />
              </Routes>
            </div>
          </main>
        </div>
      </Router>
    </QueryClientProvider>
  );
}

export default App;
