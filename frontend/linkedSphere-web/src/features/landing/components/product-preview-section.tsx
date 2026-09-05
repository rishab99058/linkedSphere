import {
  Heart,
  MessageCircle,
  MoreHorizontal,
  Repeat2,
  Send,
} from "lucide-react";

function ProductPreviewSection() {
  return (
    <section className="overflow-hidden bg-slate-950 px-5 py-24 sm:px-8 lg:px-10 lg:py-32">
      <div className="mx-auto grid max-w-7xl items-center gap-16 lg:grid-cols-[0.9fr_1.1fr]">
        {/* Copy */}
        <div>
          <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-400">
            Inside your sphere
          </p>

          <h2 className="mt-4 text-4xl font-bold tracking-tight text-white sm:text-5xl">
            A place where
            <br />
            <span className="text-blue-400">
              ideas move.
            </span>
          </h2>

          <p className="mt-6 max-w-xl text-lg leading-8 text-slate-400">
            Share what you're working on, celebrate achievements, exchange
            knowledge, and join conversations with people in your professional
            sphere.
          </p>

          <div className="mt-10 space-y-5">
            {[
              "Share your professional journey",
              "Join meaningful conversations",
              "Discover what your network is building",
            ].map((item) => (
              <div
                key={item}
                className="flex items-center gap-3"
              >
                <div className="h-2 w-2 rounded-full bg-blue-400" />

                <span className="text-sm text-slate-300">
                  {item}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* Fake product UI */}
        <div className="relative">
          <div className="absolute -inset-10 rounded-[3rem] bg-blue-600/10 blur-[80px]" />

          <div className="relative rounded-[2rem] border border-white/10 bg-white/[0.06] p-3 shadow-2xl backdrop-blur-xl">
            <div className="rounded-[1.5rem] bg-white p-5">
              {/* Header */}
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="h-11 w-11 rounded-full bg-gradient-to-br from-blue-500 to-cyan-400" />

                  <div>
                    <p className="text-sm font-bold text-slate-900">
                      Alex Morgan
                    </p>

                    <p className="text-xs text-slate-400">
                      Software Engineer • 2h
                    </p>
                  </div>
                </div>

                <MoreHorizontal className="h-5 w-5 text-slate-400" />
              </div>

              {/* Content */}
              <div className="mt-5">
                <p className="text-sm leading-7 text-slate-700">
                  Just shipped a project I've been working on for the last
                  few weeks. There is something special about seeing an idea
                  turn into something real. 🚀
                </p>
              </div>

              {/* Fake visual */}
              <div className="mt-5 flex h-48 items-center justify-center rounded-2xl bg-slate-100">
                <div className="text-center">
                  <div className="mx-auto h-16 w-16 rounded-full bg-blue-100" />

                  <p className="mt-3 text-sm font-semibold text-slate-500">
                    Your professional story
                  </p>
                </div>
              </div>

              {/* Engagement */}
              <div className="mt-5 flex items-center justify-between border-b border-slate-100 pb-4 text-xs text-slate-400">
                <span>128 reactions</span>
                <span>24 comments</span>
              </div>

              <div className="grid grid-cols-4 gap-1 pt-3">
                <button
                  type="button"
                  className="flex items-center justify-center gap-2 rounded-lg py-2 text-xs font-medium text-slate-500 hover:bg-slate-50"
                >
                  <Heart className="h-4 w-4" />
                  Like
                </button>

                <button
                  type="button"
                  className="flex items-center justify-center gap-2 rounded-lg py-2 text-xs font-medium text-slate-500 hover:bg-slate-50"
                >
                  <MessageCircle className="h-4 w-4" />
                  Comment
                </button>

                <button
                  type="button"
                  className="flex items-center justify-center gap-2 rounded-lg py-2 text-xs font-medium text-slate-500 hover:bg-slate-50"
                >
                  <Repeat2 className="h-4 w-4" />
                  Repost
                </button>

                <button
                  type="button"
                  className="flex items-center justify-center gap-2 rounded-lg py-2 text-xs font-medium text-slate-500 hover:bg-slate-50"
                >
                  <Send className="h-4 w-4" />
                  Send
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default ProductPreviewSection;