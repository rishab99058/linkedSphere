import { ArrowRight, Check } from "lucide-react";
import { Link } from "react-router-dom";

const NETWORK_IMAGE =
  "YOUR_SECOND_CLOUDINARY_IMAGE_URL";

function NetworkSection() {
  return (
    <section className="overflow-hidden bg-slate-950 px-5 py-24 sm:px-8 lg:px-10 lg:py-32">
      <div className="mx-auto grid max-w-7xl items-center gap-16 lg:grid-cols-2">
        {/* Visual */}
        <div className="relative order-2 flex min-h-[450px] items-center justify-center lg:order-1">
          <div className="absolute h-80 w-80 rounded-full bg-blue-600/20 blur-[100px]" />

          <div className="absolute h-[380px] w-[380px] rounded-full border border-white/10" />

          <div className="absolute h-[280px] w-[280px] rounded-full border border-blue-400/10" />

          <img
            src="https://res.cloudinary.com/dws1oujlk/image/upload/v1786861410/ChatGPT_Image_Aug_16_2026_11_46_26_AM_h7fngh.png"
            alt="Build your professional network"
            className="relative z-10 w-full max-w-[480px] object-contain drop-shadow-[0_30px_70px_rgba(37,99,235,0.35)]"
          />
        </div>

        {/* Content */}
        <div className="order-1 lg:order-2">
          <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-400">
            Your sphere
          </p>

          <h2 className="mt-4 text-4xl font-bold leading-tight tracking-tight text-white sm:text-5xl">
            Build your network
            <br />
            <span className="text-blue-400">
              intentionally.
            </span>
          </h2>

          <p className="mt-6 max-w-xl text-lg leading-8 text-slate-400">
            The strongest professional networks are built around genuine
            relationships. Follow people who inspire you, participate in
            conversations, and create your own professional identity.
          </p>

          <div className="mt-10 space-y-5">
            {[
              "Meet people beyond your immediate circle",
              "Share knowledge and experiences",
              "Turn conversations into opportunities",
            ].map((item) => (
              <div
                key={item}
                className="flex items-start gap-3"
              >
                <div className="mt-1 flex h-5 w-5 shrink-0 items-center justify-center rounded-full bg-blue-400/10">
                  <Check className="h-3 w-3 text-blue-400" />
                </div>

                <span className="text-sm leading-6 text-slate-300">
                  {item}
                </span>
              </div>
            ))}
          </div>

          <Link
            to="/auth/register"
            className="mt-10 inline-flex items-center gap-2 text-sm font-semibold text-blue-400 transition-colors hover:text-blue-300"
          >
            Start building your sphere
            <ArrowRight className="h-4 w-4" />
          </Link>
        </div>
      </div>
    </section>
  );
}

export default NetworkSection;