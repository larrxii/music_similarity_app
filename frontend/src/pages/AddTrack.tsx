import { useState } from 'react';
import { ArrowLeft, Music2, Save, X } from 'lucide-react';
import { useNavigate } from 'react-router';
import { toast } from 'sonner';
import api from '../api/axios';

interface TrackData {
  track_id: string;
  track_name: string;
  artist_name: string;
  year: string;
  popularity: string;
  artwork_url: string;
  album_name: string;
  acousticness: string;
  danceability: string;
  duration_ms: string;
  energy: string;
  key: string;
  liveness: string;
  loudness: string;
  mode: string;
  speechiness: string;
  tempo: string;
  time_signature: string;
  valence: string;
  track_url: string;
  language: string;
}

export default function AddTrack() {
  const navigate = useNavigate();
  
const [formData, setFormData] = useState<TrackData>({
    track_id: '',
    track_name: '',
    artist_name: '',
    year: '',
    popularity: '',
    artwork_url: '',
    album_name: '',
    acousticness: '',
    danceability: '',
    duration_ms: '',
    energy: '',
    key: '',
    liveness: '',
    loudness: '',
    mode: '',
    speechiness: '',
    tempo: '',
    time_signature: '',
    valence: '',
    track_url: '',
    language: '',
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.track_id || !formData.track_name) {
      toast.error('Track ID and Track Name are required!');
      return;
    }

    try {
      await api.post('/api/tracks', formData);   // ← обрати внимание на /api
      toast.success('Трек успешно добавлен в базу!');
      handleReset();
    } catch (error: any) {
      toast.error(error.response?.data?.error || 'Ошибка при сохранении');
    }
};

  const handleReset = () => {
    setFormData({
      track_id: '',
      track_name: '',
      artist_name: '',
      year: '',
      popularity: '',
      artwork_url: '',
      album_name: '',
      acousticness: '',
      danceability: '',
      duration_ms: '',
      energy: '',
      key: '',
      liveness: '',
      loudness: '',
      mode: '',
      speechiness: '',
      tempo: '',
      time_signature: '',
      valence: '',
      track_url: '',
      language: '',
    });
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-200 via-pink-200 to-blue-200 py-12 px-4">
      <div className="max-w-5xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <button
            onClick={() => navigate('/')}
            className="neomorph-flat px-4 py-2 rounded-lg text-purple-700 hover:text-purple-900 transition-colors flex items-center gap-2 mb-6"
          >
            <ArrowLeft className="w-5 h-5" />
            Back to Home
          </button>
          
          <div className="text-center">
            <div className="inline-flex items-center justify-center w-20 h-20 rounded-full mb-4 neomorph-raised bg-gradient-to-br from-purple-400 to-pink-400">
              <Music2 className="w-10 h-10 text-white" />
            </div>
            <h1 className="text-purple-900 mb-2">Add Track Data</h1>
            <p className="text-purple-700">Enter track details for similarity analysis</p>
          </div>
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="neomorph-card rounded-3xl p-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Required Fields */}
            <div className="md:col-span-2">
              <h3 className="text-purple-900 mb-4 flex items-center gap-2">
                <span className="neomorph-badge px-3 py-1 rounded-lg text-white text-sm">Required</span>
                Basic Information
              </h3>
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Track ID *</label>
              <input
                type="text"
                name="track_id"
                value={formData.track_id}
                onChange={handleInputChange}
                placeholder="Spotify Track ID"
                required
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Track Name *</label>
              <input
                type="text"
                name="track_name"
                value={formData.track_name}
                onChange={handleInputChange}
                placeholder="Name of the track"
                required
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            {/* Optional Fields */}
            <div className="md:col-span-2 mt-4">
              <h3 className="text-purple-900 mb-4">Optional Information</h3>
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Artist Name</label>
              <input
                type="text"
                name="artist_name"
                value={formData.artist_name}
                onChange={handleInputChange}
                placeholder="Artist name(s), comma-separated"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Album Name</label>
              <input
                type="text"
                name="album_name"
                value={formData.album_name}
                onChange={handleInputChange}
                placeholder="Album name"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Year</label>
              <input
                type="number"
                name="year"
                value={formData.year}
                onChange={handleInputChange}
                placeholder="Release year"
                min="1900"
                max="2100"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Language</label>
              <select
                name="language"
                value={formData.language}
                onChange={handleInputChange}
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              >
                <option value="">Select language</option>
                <option value="English">English</option>
                <option value="Tamil">Tamil</option>
                <option value="Hindi">Hindi</option>
                <option value="Telugu">Telugu</option>
                <option value="Malayalam">Malayalam</option>
                <option value="Korean">Korean</option>
              </select>
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Popularity (0-100)</label>
              <input
                type="number"
                name="popularity"
                value={formData.popularity}
                onChange={handleInputChange}
                placeholder="0-100"
                min="0"
                max="100"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Duration (ms)</label>
              <input
                type="number"
                name="duration_ms"
                value={formData.duration_ms}
                onChange={handleInputChange}
                placeholder="Track length in milliseconds"
                min="0"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            {/* Audio Features */}
            <div className="md:col-span-2 mt-4">
              <h3 className="text-purple-900 mb-4">Audio Features (0.0 - 1.0)</h3>
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Acousticness</label>
              <input
                type="number"
                name="acousticness"
                value={formData.acousticness}
                onChange={handleInputChange}
                placeholder="0.0 - 1.0"
                min="0"
                max="1"
                step="0.01"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Danceability</label>
              <input
                type="number"
                name="danceability"
                value={formData.danceability}
                onChange={handleInputChange}
                placeholder="0.0 - 1.0"
                min="0"
                max="1"
                step="0.01"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Energy</label>
              <input
                type="number"
                name="energy"
                value={formData.energy}
                onChange={handleInputChange}
                placeholder="0.0 - 1.0"
                min="0"
                max="1"
                step="0.01"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Liveness</label>
              <input
                type="number"
                name="liveness"
                value={formData.liveness}
                onChange={handleInputChange}
                placeholder="0.0 - 1.0"
                min="0"
                max="1"
                step="0.01"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Speechiness</label>
              <input
                type="number"
                name="speechiness"
                value={formData.speechiness}
                onChange={handleInputChange}
                placeholder="0.0 - 1.0"
                min="0"
                max="1"
                step="0.01"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Valence</label>
              <input
                type="number"
                name="valence"
                value={formData.valence}
                onChange={handleInputChange}
                placeholder="0.0 - 1.0 (positivity)"
                min="0"
                max="1"
                step="0.01"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            {/* Musical Properties */}
            <div className="md:col-span-2 mt-4">
              <h3 className="text-purple-900 mb-4">Musical Properties</h3>
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Key (0-11)</label>
              <input
                type="number"
                name="key"
                value={formData.key}
                onChange={handleInputChange}
                placeholder="0=C, 1=C♯/D♭, etc."
                min="0"
                max="11"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Mode</label>
              <select
                name="mode"
                value={formData.mode}
                onChange={handleInputChange}
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              >
                <option value="">Select mode</option>
                <option value="0">Minor (0)</option>
                <option value="1">Major (1)</option>
              </select>
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Tempo (BPM)</label>
              <input
                type="number"
                name="tempo"
                value={formData.tempo}
                onChange={handleInputChange}
                placeholder="Beats per minute"
                min="0"
                step="0.1"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Time Signature (3-7)</label>
              <input
                type="number"
                name="time_signature"
                value={formData.time_signature}
                onChange={handleInputChange}
                placeholder="Beats per measure"
                min="3"
                max="7"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div>
              <label className="block text-purple-800 mb-2 text-sm">Loudness (dB)</label>
              <input
                type="number"
                name="loudness"
                value={formData.loudness}
                onChange={handleInputChange}
                placeholder="Loudness in decibels"
                step="0.1"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            {/* URLs */}
            <div className="md:col-span-2 mt-4">
              <h3 className="text-purple-900 mb-4">URLs</h3>
            </div>

            <div className="md:col-span-2">
              <label className="block text-purple-800 mb-2 text-sm">Track URL</label>
              <input
                type="url"
                name="track_url"
                value={formData.track_url}
                onChange={handleInputChange}
                placeholder="Spotify URL for the track"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>

            <div className="md:col-span-2">
              <label className="block text-purple-800 mb-2 text-sm">Artwork URL</label>
              <input
                type="url"
                name="artwork_url"
                value={formData.artwork_url}
                onChange={handleInputChange}
                placeholder="URL of album/track artwork"
                className="w-full px-4 py-3 rounded-xl text-purple-900 placeholder-purple-400 neomorph-input"
              />
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex gap-4 mt-8 justify-end">
            <button
              type="button"
              onClick={handleReset}
              className="neomorph-flat px-6 py-3 rounded-xl text-purple-700 hover:text-purple-900 transition-all flex items-center gap-2"
            >
              <X className="w-5 h-5" />
              Reset
            </button>
            <button
              type="submit"
              className="neomorph-button-colored px-8 py-3 rounded-xl text-white transition-all flex items-center gap-2 hover:scale-105"
            >
              <Save className="w-5 h-5" />
              Save Track
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
