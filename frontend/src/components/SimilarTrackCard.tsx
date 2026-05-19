import { Play } from "lucide-react";
import { ImageWithFallback } from "./ImageWithFallback";

interface Track {
  id: string | number;
  name: string;
  artistName?: string;
  imageUrl?: string;
  similarity?: number;
}

export function SimilarTrackCard({ track }: { track: Track }) {
  const imageSrc =
    track.imageUrl ||
    "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400";
    
    const searchQuery = encodeURIComponent(
    `${track.artistName || ""} ${track.name}`
    );

    const musicUrl = `https://www.youtube.com/results?search_query=${searchQuery}`;

  return (
    <a href={musicUrl} target="_blank" rel="noopener noreferrer" className="block">
        <div className="neomorph-card rounded-2xl p-6 group cursor-pointer">
        
            {/* Track Image */}
            <div className="relative mb-4 overflow-hidden rounded-xl aspect-square">
                <ImageWithFallback
                src={imageSrc}
                alt={track.name}
                className="w-full h-full object-cover"
                />

                {/* Overlay */}
                <div className="absolute inset-0 bg-gradient-to-br from-purple-900/0 to-pink-900/0 group-hover:from-purple-900/40 group-hover:to-pink-900/40 transition-all duration-300 flex items-center justify-center">
                
                <button className="play-button opacity-0 group-hover:opacity-100 transform scale-75 group-hover:scale-100 transition-all duration-300 w-14 h-14 rounded-full flex items-center justify-center">
                    <Play className="w-6 h-6 text-white fill-white" />
                </button>

                </div>

                {/* Similarity badge */}
                <div className="absolute top-3 right-3">
                <div className="neomorph-badge rounded-lg px-3 py-1 text-sm text-white backdrop-blur-sm">
                    {track.similarity ?? 0}% match
                </div>
                </div>
            </div>

            {/* Track Info */}
            <div className="space-y-1">
                <h3 className="text-purple-900 truncate">
                {track.name}
                </h3>

                <p className="text-purple-600 text-sm">
                {track.artistName}
                </p>
            </div>
        </div>
    </a>

  );
}