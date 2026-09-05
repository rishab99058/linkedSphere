import {
  ArrowDown,
  ArrowRight,
  Check,
  Sparkles,
} from "lucide-react";
import { Link } from "react-router-dom";

const HERO_IMAGE =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

function HeroSection() {
  return (
    <section className="relative min-h-screen overflow-hidden bg-slate-950">
      {/* Background glow */}
      <div className="absolute left-[10%] top-[15%] h-[500px] w-[500px] rounded-full bg-blue-600/20 blur-[140px]" />

      <div className="absolute bottom-[-10%] right-[-5%] h-[500px] w-[500px] rounded-full bg-cyan-500/10 blur-[140px]" />

      {/* Grid */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,rgba(148,163,184,0.045)_1px,transparent_1px),linear-gradient(to_bottom,rgba(148,163,184,0.045)_1px,transparent_1px)] bg-[size:72px_72px]" />

      {/* Main content */}
      <div className="relative mx-auto grid min-h-screen max-w-7xl items-center gap-10 px-5 pb-16 pt-28 sm:px-8 lg:grid-cols-[1.05fr_0.95fr] lg:px-10 lg:pt-32">
        {/* Copy */}
        <div className="relative z-10">
          <div className="mb-7 inline-flex items-center gap-2 rounded-full border border-blue-400/20 bg-blue-400/10 px-4 py-2 text-sm font-medium text-blue-300">
            <Sparkles className="h-4 w-4" />
            Build your professional sphere
          </div>

          <h1 className="max-w-3xl text-5xl font-bold leading-[1.02] tracking-[-0.04em] text-white sm:text-6xl lg:text-7xl">
            Your professional
            <br />
            world,
            <span className="bg-gradient-to-r from-blue-400 via-cyan-300 to-blue-500 bg-clip-text text-transparent">
              {" "}
              connected.
            </span>
          </h1>

          <p className="mt-7 max-w-xl text-base leading-8 text-slate-400 sm:text-lg">
            Discover people who inspire you, share ideas that matter, and
            create meaningful professional opportunities — all within your
            sphere.
          </p>

          <div className="mt-9 flex flex-col gap-3 sm:flex-row">
            <Link
              to="/auth/register"
              className="group inline-flex items-center justify-center gap-2 rounded-xl bg-blue-600 px-6 py-3.5 text-sm font-semibold text-white shadow-xl shadow-blue-600/20 transition-all hover:bg-blue-500 hover:shadow-blue-500/30"
            >
              Create your sphere

              <ArrowRight className="h-4 w-4 transition-transform group-hover:translate-x-1" />
            </Link>

            <Link
              to="/auth/login"
              className="inline-flex items-center justify-center rounded-xl border border-white/15 bg-white/5 px-6 py-3.5 text-sm font-semibold text-white backdrop-blur-sm transition-colors hover:bg-white/10"
            >
              Sign in
            </Link>
          </div>

          <div className="mt-8 flex flex-wrap gap-x-6 gap-y-3 text-sm text-slate-500">
            <span className="flex items-center gap-2">
              <Check className="h-4 w-4 text-blue-400" />
              Connect
            </span>

            <span className="flex items-center gap-2">
              <Check className="h-4 w-4 text-blue-400" />
              Discover
            </span>

            <span className="flex items-center gap-2">
              <Check className="h-4 w-4 text-blue-400" />
              Grow
            </span>
          </div>
        </div>

        {/* Visual */}
        <div className="relative flex min-h-[450px] items-center justify-center lg:min-h-[650px]">
          <div className="absolute h-[300px] w-[300px] rounded-full bg-blue-500/20 blur-[90px] sm:h-[430px] sm:w-[430px]" />

          <div className="absolute h-[330px] w-[330px] rounded-full border border-blue-400/10 sm:h-[480px] sm:w-[480px]" />

          <div className="absolute h-[230px] w-[230px] rounded-full border border-cyan-400/10 sm:h-[350px] sm:w-[350px]" />

          <img
            src={HERO_IMAGE}
            alt="LinkedSphere"
            className="relative z-10 w-full max-w-[460px] object-contain drop-shadow-[0_35px_80px_rgba(37,99,235,0.4)]"
          />

          {/* Floating cards */}
          <div className="absolute right-0 top-24 z-20 hidden rounded-2xl border border-white/10 bg-white/10 px-4 py-3 shadow-2xl backdrop-blur-xl sm:block">
            <p className="text-xs text-slate-400">
              Your sphere
            </p>

            <p className="mt-1 text-sm font-semibold text-white">
              People • Ideas • Opportunities
            </p>
          </div>

          <div className="absolute bottom-24 left-0 z-20 hidden rounded-2xl border border-white/10 bg-white/10 px-4 py-3 shadow-2xl backdrop-blur-xl sm:block">
            <p className="text-xs text-slate-400">
              Built around
            </p>

            <p className="mt-1 text-sm font-semibold text-white">
              Meaningful connections
            </p>
          </div>
        </div>
      </div>

      {/* Scroll indicator */}
      <a
        href="#about"
        className="absolute bottom-7 left-1/2 hidden -translate-x-1/2 flex-col items-center gap-2 text-xs text-slate-500 transition-colors hover:text-slate-300 sm:flex"
      >
        <span>Explore LinkedSphere</span>

        <ArrowDown className="h-4 w-4 animate-bounce" />
      </a>
    </section>
  );
}

export default HeroSection;