// главный файл приложения

import { useState } from "react";
import { Search, Music } from "lucide-react";
import { SimilarArtistCard } from "./components/SimilarArtistCard";

interface Artist {
  // интерфейс нужен для типизации артиста из бэкенда
  id: number;
  spotifyId: string;
  name: string;
  genre?: string | null;
  imageUrl?: string;
  popularity?: number;
}

export default function App() {
  // экспортируем главный компонент
  const [searchInput, setSearchInput] = useState(""); // устанавливаем состояние для ввода поиска
  const [searchedArtist, setSearchedArtist] = useState(""); // состояние для отображения текущего искомого артиста
  const [similarArtists, setSimilarArtists] = useState<any[]>([]); // состояние для списка похожих артистов
  const [isSearching, setIsSearching] = useState(false); // состояние для индикатора поиска

  const handleSearch = async () => {
    if (!searchInput.trim()) return; // если строка пустая, ничего не делаем

    setIsSearching(true); // устанавливаем состояние поиска в true
    setSearchedArtist(searchInput); // сохраняем текущий поисковый запрос

    try {
      // 1. Ищем артистов через бэкенд
      const response = await fetch(
        `http://localhost:8080/api/artists/search?query=${encodeURIComponent(
          // encodeuriComponent кодирует строку для URL
          searchInput
        )}&limit=4` // ограничиваем результат 4 артистами
      );

      if (!response.ok) {
        throw new Error(`API request failed: ${response.statusText}`);
      }

      const artists: Artist[] = await response.json(); // получаем массив артистов из ответа

      console.log("Artists from backend:", artists);

      // 2. Преобразуем данные для отображения в карточках
      const artistsForDisplay = artists.map((artist, index) => {
        // map преобразует каждый объект артиста в формат для карточки
        // Используем imageUrl из ответа бэкенда
        const imageUrl = artist.imageUrl;

        // Если нет картинки, используем заглушку
        const fallbackImages = [
          "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400",
          "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=400",
          "https://images.unsplash.com/photo-1514320291840-2e0a9bf2a9ae?w=400",
          "https://images.unsplash.com/photo-1498038432885-c6f3f1b912ee?w=400",
        ];

        return {
          id: artist.id || artist.spotifyId || `artist-${index}`,
          name: artist.name,
          genre: artist.genre || "Various",
          // Вычисляем similarity на основе popularity (если есть) или используем рандом
          similarity: artist.popularity
            ? Math.min(95, 70 + (artist.popularity / 100) * 25)
            : 80 + index * 4,
          image: imageUrl || fallbackImages[index % fallbackImages.length],
          popularity: artist.popularity,
        };
      });

      setSimilarArtists(artistsForDisplay);
    } catch (error) {
      console.error("Search error:", error);
      // В случае ошибки показываем заглушку
      // setSimilarArtists([ // заглушечные данные которые можно удалить
      //   {
      //     id: "error-1",
      //     name: "Taylor Swift",
      //     genre: "Pop",
      //     similarity: 85,
      //     image:
      //       "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400",
      //   },
      //   {
      //     id: "error-2",
      //     name: "Taylor Swift Piano Covers",
      //     genre: "Instrumental",
      //     similarity: 85,
      //     image:
      //       "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?w=400",
      //   },
      // ]);
    } finally {
      setIsSearching(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    // обработка нажатия клавиши
    if (e.key === "Enter") {
      handleSearch(); // если нажата Enter, запускаем поиск
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-200 via-pink-200 to-blue-200 py-12 px-4">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="text-center mb-16">
          <div className="inline-flex items-center justify-center w-24 h-24 rounded-full mb-6 neomorph-raised bg-gradient-to-br from-purple-400 to-pink-400">
            <Music className="w-12 h-12 text-white" />
          </div>
          <h1 className="text-purple-900 mb-3">Music Discovery</h1>
          <p className="text-purple-700">
            Find artists similar to your favorites
          </p>
        </div>

        {/* Search Section */}
        <div className="max-w-2xl mx-auto mb-16">
          <div className="neomorph-inset rounded-2xl p-2">
            <div className="flex items-center gap-3 px-4">
              <Search className="w-5 h-5 text-purple-500" />
              <input
                type="text"
                value={searchInput}
                onChange={(e) => setSearchInput(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder="Enter artist name..."
                className="flex-1 bg-transparent border-none outline-none py-4 text-purple-900 placeholder-purple-400"
              />
              <button
                onClick={handleSearch}
                disabled={isSearching}
                className="neomorph-button-colored px-8 py-3 rounded-xl text-white transition-all disabled:opacity-50"
              >
                {isSearching ? "Searching..." : "Search"}
              </button>
            </div>
          </div>

          {/* Quick search suggestions */}
          {!searchedArtist && (
            <div className="mt-6 flex flex-wrap gap-3 justify-center">
              <span className="text-purple-700 text-sm">Try:</span>
              {["Taylor Swift", "Eminem", "BTS"].map((artist) => (
                <button
                  key={artist}
                  onClick={() => {
                    setSearchInput(artist);
                    setTimeout(() => handleSearch(), 100);
                  }}
                  className="neomorph-flat px-4 py-2 rounded-lg text-purple-700 text-sm hover:text-purple-900 transition-colors"
                >
                  {artist}
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Results Section - КВАДРАТНЫЕ КАРТОЧКИ ПОД ПОИСКОМ */}
        {searchedArtist && (
          <div>
            <h2 className="text-center text-purple-900 mb-8">
              Search results for{" "}
              <span className="text-pink-600">{searchedArtist}</span>
            </h2>

            {isSearching ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {/* Skeleton loading */}
                {[1, 2, 3, 4].map((i) => (
                  <div
                    key={i}
                    className="neomorph-flat rounded-2xl p-6 animate-pulse"
                  >
                    <div className="aspect-square bg-purple-300 rounded-xl mb-4"></div>
                    <div className="h-4 bg-purple-300 rounded mb-2"></div>
                    <div className="h-3 bg-purple-300 rounded w-2/3"></div>
                  </div>
                ))}
              </div>
            ) : similarArtists.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {similarArtists.map((artist) => (
                  <SimilarArtistCard key={artist.id} artist={artist} />
                ))}
              </div>
            ) : (
              <div className="text-center py-12">
                <div className="inline-block neomorph-inset rounded-full p-8 mb-6">
                  <Search className="w-16 h-16 text-purple-400" />
                </div>
                <p className="text-purple-700">
                  No artists found. Try another search.
                </p>
              </div>
            )}
          </div>
        )}

        {/* Empty State - когда еще ничего не искали */}
        {!searchedArtist && (
          <div className="text-center mt-16">
            <div className="inline-block neomorph-inset rounded-full p-8 mb-6">
              <Search className="w-16 h-16 text-purple-400" />
            </div>
            <p className="text-purple-700">
              Enter an artist name to discover music
            </p>
            <p className="text-purple-600 text-sm mt-2">
              Example: Search for "Taylor Swift" to see results
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
