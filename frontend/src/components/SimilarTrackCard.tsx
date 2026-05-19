interface Track {
  id: string | number;
  name: string;
  artistName?: string;
  imageUrl?: string;
  similarity?: number;
}

export function SimilarTrackCard({ track }: { track: Track }) {
  return (
    <div className="neomorph-card rounded-2xl p-6">
      <img
        src={track.imageUrl}
        alt={track.name}
        className="rounded-xl mb-4"
      />

      <h3>{track.name}</h3>

      <p>{track.artistName}</p>

      <p>{track.similarity}% match</p>
    </div>
  );
}