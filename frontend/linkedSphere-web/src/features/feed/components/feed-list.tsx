import PostCard from "./post-card";

const mockPosts = [
  {
    id: 1,
    author: "Komal",
    time: "2h ago",
    content:
      "Building LinkedSphere — a professional networking platform focused on connecting people, skills and opportunities.",
  },
  {
    id: 2,
    author: "Akhand",
    time: "5h ago",
    content:
      "Welcome to LinkedSphere! Build your professional identity and discover new opportunities.",
  },
  {
    id: 3,
    author: "Munni",
    time: "1d ago",
    content:
      "Just finished working on an exciting new project. Looking forward to sharing more soon!",
  },
];

function FeedList() {
  return (
    <div className="space-y-4">
      {mockPosts.map((post) => (
        <PostCard
          key={post.id}
          author={post.author}
          time={post.time}
          content={post.content}
        />
      ))}
    </div>
  );
}

export default FeedList;