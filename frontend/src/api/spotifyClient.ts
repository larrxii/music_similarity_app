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

export const spotifyClient = {
  // функция поиска артистов
  searchArtists: async (query: string, limit: number = 10): Promise<any[]> => {
    try {
      const response = await fetch(
        `${API_BASE_URL}/artists/search?query=${encodeURIComponent(
          query
        )}&limit=${limit}`
      );

      if (!response.ok) {
        throw new Error(`API request failed: ${response.statusText}`);
      }

      return await response.json();
    } catch (error) {
      console.error("Error searching artists:", error);
      return [];
    }
  },
};
