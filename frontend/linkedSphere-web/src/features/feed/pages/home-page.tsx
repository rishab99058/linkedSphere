import Navbar from "../components/navbar";
import CreatePost from "../components/create-post";
import FeedList from "../components/feed-list";

function HomePage() {
  return (
    <div className="min-h-screen bg-slate-100">
      <Navbar />

      <main className="mx-auto max-w-3xl px-4 py-6">
        <CreatePost />

        <div className="mt-6">
          <FeedList />
        </div>
      </main>
    </div>
  );
}

export default HomePage;