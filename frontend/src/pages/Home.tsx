import { useState } from 'react';
import { Music, Search } from 'lucide-react';
import { SimilarArtistCard } from '../components/SimilarArtistCard';
import { SimilarTrackCard } from '../components/SimilarTrackCard';
import { spotifyClient } from '../api/spotifyClient'; // ваш клиент

export default function Home() {
  const [searchInput, setSearchInput] = useState("");
  const [searchedItem, setSearchedItem] = useState("");
  const [similarItems, setSimilarItems] = useState<any[]>([]);
  const [isSearching, setIsSearching] = useState(false);
  const [searchType, setSearchType] = useState<"artist" | "track">("artist");

  const handleSearch = async () => {
    if (!searchInput.trim()) return;
    
    setIsSearching(true);
    setSearchedItem(searchInput);

    try {
      const results = searchType === "artist"
        ? await spotifyClient.getSimilarArtists(searchInput)
        : await spotifyClient.getSimilarTracks(searchInput);

      const itemsForDisplay = results.map((item: any, index: number) => ({
        id: item.id || index,
        name: searchType === "artist" ? item.name : item.trackName,
        artistName: searchType === "track" ? item.artistName : undefined,
        genre: item.genre || "Various",
        similarity: 85 + index * 2,
        imageUrl: item.imageUrl,
        popularity: item.popularity,
      }));

      setSimilarItems(itemsForDisplay);
    } catch (error) {
      console.error("Search error:", error);
      setSimilarItems([]);
    } finally {
      setIsSearching(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") handleSearch();
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-200 via-pink-200 to-blue-200 py-12 px-4">
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="text-center mb-16">
          <div className="inline-flex items-center justify-center w-24 h-24 rounded-full mb-6 bg-gradient-to-br from-purple-500 to-pink-500">
            <Music className="w-12 h-12 text-white" />
          </div>
          <h1 className="text-4xl font-bold text-purple-900 mb-3">Music Discovery</h1>
          <p className="text-purple-700 text-lg">
            {searchType === "artist" 
              ? "Find artists similar to your favorites" 
              : "Find tracks similar to your favorites"}
          </p>
        </div>

        {/* Search */}
        <div className="max-w-2xl mx-auto mb-16">
          <div className="neomorph-inset rounded-3xl p-2">
            <div className="flex items-center gap-4 px-6 py-2">
              <div className="flex gap-2">
                <button 
                  onClick={() => setSearchType("artist")}
                  className={`px-5 py-2 rounded-xl transition-all ${searchType === 'artist' ? 'bg-purple-600 text-white' : 'text-purple-700'}`}
                >
                  Artists
                </button>
                <button 
                  onClick={() => setSearchType("track")}
                  className={`px-5 py-2 rounded-xl transition-all ${searchType === 'track' ? 'bg-purple-600 text-white' : 'text-purple-700'}`}
                >
                  Tracks
                </button>
              </div>

              <Search className="w-5 h-5 text-purple-500" />
              <input
                type="text"
                value={searchInput}
                onChange={(e) => setSearchInput(e.target.value)}
                onKeyPress={handleKeyPress}
                placeholder={searchType === "artist" ? "Enter artist name..." : "Enter track name..."}
                className="flex-1 bg-transparent border-none outline-none py-4 text-purple-900 placeholder-purple-400 text-lg"
              />
              <button
                onClick={handleSearch}
                disabled={isSearching}
                className="neomorph-button-colored px-8 py-3 rounded-2xl text-white font-medium disabled:opacity-70"
              >
                {isSearching ? "Searching..." : "Search"}
              </button>
            </div>
          </div>

          {!searchedItem && (
            <div className="mt-6 flex flex-wrap gap-3 justify-center">
              {["Taylor Swift", "Eminem", "BTS", "Drake"].map((s) => (
                <button
                  key={s}
                  onClick={() => { setSearchInput(s); setTimeout(handleSearch, 80); }}
                  className="neomorph-flat px-5 py-2 rounded-xl text-sm text-purple-700 hover:text-purple-900"
                >
                  {s}
                </button>
              ))}
            </div>
          )}
        </div>

        {/* Results */}
        {searchedItem && (
          <div>
            <h2 className="text-center text-2xl text-purple-900 mb-8">
              Results for <span className="text-pink-600">"{searchedItem}"</span>
            </h2>

            {isSearching ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {[1,2,3,4].map(i => <SkeletonCard key={i} />)}
              </div>
            ) : similarItems.length > 0 ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                {similarItems.map(item =>
                  searchType === "artist" ? 
                    <SimilarArtistCard key={item.id} artist={item} /> : 
                    <SimilarTrackCard key={item.id} track={item} />
                )}
              </div>
            ) : (
              <div className="text-center py-20 text-purple-600">
                No results found. Try another search.
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

const SkeletonCard = () => (
  <div className="neomorph-flat rounded-2xl p-6 animate-pulse">
    <div className="aspect-square bg-purple-300 rounded-xl mb-4" />
    <div className="h-5 bg-purple-300 rounded mb-2 w-3/4" />
    <div className="h-4 bg-purple-300 rounded w-1/2" />
  </div>
);