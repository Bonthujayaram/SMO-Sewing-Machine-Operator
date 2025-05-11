import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  optimizeDeps: {
    exclude: ['lucide-react'],
  },
  resolve: {
    alias: {
      // Map the deep import to the correct absolute path
      'react-calendar-timeline/lib/Timeline.css': '/node_modules/react-calendar-timeline/lib/Timeline.css'
    }
  }
});
