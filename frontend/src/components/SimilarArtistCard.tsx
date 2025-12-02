// компонент карточки похожего артиста

import { Play } from "lucide-react";
import { ImageWithFallback } from "./ImageWithFallback";

interface Artist {
  id: string | number;
  spotifyId?: string;
  name: string;
  genre?: string | null;
  image?: string;
  similarity?: number;
  popularity?: number;
}

interface SimilarArtistCardProps {
  artist: Artist;
}

export function SimilarArtistCard({ artist }: SimilarArtistCardProps) {
  const imageSrc =
    artist.image ||
    (artist as any).imageUrl || // попытка получить imageUrl, если image отсутствует
    "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400";
  console.log("SimilarArtistCard:", artist.name, "imageSrc:", imageSrc); // для отладки
  return (
    <div className="neomorph-card rounded-2xl p-6 group cursor-pointer">
      {/* Artist Image */}
      <div className="relative mb-4 overflow-hidden rounded-xl aspect-square">
        <ImageWithFallback
          src={imageSrc}
          alt={artist.name}
          className="w-full h-full object-cover"
          onError={(e) => {
            console.error(`Failed to load image for ${artist.name}:`, imageSrc);
          }}
        />

        {/* Play Button Overlay */}
        <div className="absolute inset-0 bg-gradient-to-br from-purple-900/0 to-pink-900/0 group-hover:from-purple-900/40 group-hover:to-pink-900/40 transition-all duration-300 flex items-center justify-center">
          <button className="play-button opacity-0 group-hover:opacity-100 transform scale-75 group-hover:scale-100 transition-all duration-300 w-14 h-14 rounded-full flex items-center justify-center">
            <Play className="w-6 h-6 text-white fill-white" />
          </button>
        </div>

        {/* Similarity Badge */}
        <div className="absolute top-3 right-3">
          <div className="neomorph-badge rounded-lg px-3 py-1 text-sm text-white backdrop-blur-sm">
            {artist.similarity}% match
          </div>
        </div>
      </div>

      {/* Artist Info */}
      <div className="space-y-1">
        <h3 className="text-purple-900 truncate">{artist.name}</h3>
        <p className="text-purple-600 text-sm">{artist.genre}</p>
      </div>
    </div>
  );
}
