// клиент для взаимодействия с вашим бэкендом, который общается с Spotify API
const API_BASE_URL = "http://localhost:8080/api";

export interface Artist {
  id: string; // внутреннее ID
  spotifyId?: string; // ID в Spotify
  name: string;
  genre?: string | null;
  imageUrl?: string;
  popularity?: number;
  similarity?: number; // для похожих артистов
}

export interface Track {
  id: string;
  name: string;
  artistName?: string;
  imageUrl?: string;
  popularity?: number;
}

export const spotifyClient = {
  searchArtists: async (query: string, limit: number = 10) => {
    const response = await fetch(
      `${API_BASE_URL}/artists/search?query=${encodeURIComponent(query)}&limit=${limit}`
    );

    if (!response.ok) throw new Error("Search failed");
    return response.json();
  },

  getSimilarArtists: async (artistName: string) => {
    const response = await fetch(
      `${API_BASE_URL}/recommend/${encodeURIComponent(artistName)}`
    );

    if (!response.ok) throw new Error("Recommendation failed");
    return response.json();
  },

  getSimilarTracks: async (trackName: string) => {
    const response = await fetch(
      `${API_BASE_URL}/recommend/tracks/${encodeURIComponent(trackName)}`
    );

    if (!response.ok) throw new Error("Track recommendation failed");
    return response.json();
  },
};