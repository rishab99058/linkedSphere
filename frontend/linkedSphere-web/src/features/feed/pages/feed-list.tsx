import PostCard from "./post-card";

function FeedList() {
  return (
    <div className="space-y-4">
      <PostCard
        name="LinkedSphere"
        headline="Building meaningful professional connections"
        content="Welcome to LinkedSphere. Your professional network starts here."
      />

      <PostCard
        name="LinkedSphere Team"
        headline="Connect. Discover. Grow."
        content="Share ideas, discover opportunities and grow together."
      />
    </div>
  );
}

export default FeedList;