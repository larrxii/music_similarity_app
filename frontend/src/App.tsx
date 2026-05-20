import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import AddTrack from './pages/AddTrack';

export default function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gradient-to-br from-purple-200 via-pink-200 to-blue-200">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/add-track" element={<AddTrack />} />
        </Routes>
      </div>
    </Router>
  );
}