import { ArrowRight, Sparkles } from "lucide-react";
import { Link } from "react-router-dom";

const HERO_IMAGE =
  "https://res.cloudinary.com/up1blk1m/image/upload/v1788800725/network.png";

function HeroSection() {
  return (
    <section className="relative min-h-screen overflow-hidden bg-[#020617] pt-28">
      {/* Ambient background */}
      <div className="pointer-events-none absolute inset-0">
        {/* Main blue atmosphere */}
        <div className="absolute left-[55%] top-[25%] h-[500px] w-[500px] -translate-x-1/2 rounded-full bg-blue-600/[0.10] blur-[140px]" />

        <div className="absolute right-[-10%] top-[15%] h-[350px] w-[350px] rounded-full bg-cyan-400/[0.06] blur-[120px]" />

        {/* Very subtle grid */}
        <div
          className="absolute inset-0 opacity-[0.035]"
          style={{
            backgroundImage: `
              linear-gradient(rgba(96,165,250,0.6) 1px, transparent 1px),
              linear-gradient(90deg, rgba(96,165,250,0.6) 1px, transparent 1px)
            `,
            backgroundSize: "52px 52px",
          }}
        />

        {/* Top fade */}
        <div className="absolute inset-x-0 top-0 h-40 bg-gradient-to-b from-[#020617] to-transparent" />

        {/* Bottom fade */}
        <div className="absolute inset-x-0 bottom-0 h-48 bg-gradient-to-t from-[#020617] to-transparent" />
      </div>

      <div className="relative mx-auto grid min-h-[calc(100vh-7rem)] max-w-7xl items-center gap-10 px-6 pb-20 lg:grid-cols-[0.9fr_1.1fr] lg:gap-4">
        {/* Left content */}
        <div className="relative z-20 max-w-2xl">
          {/* Eyebrow */}
          <div className="mb-7 inline-flex items-center gap-2 rounded-full border border-blue-400/15 bg-blue-400/[0.05] px-3.5 py-2">
            <span className="flex h-5 w-5 items-center justify-center rounded-full bg-blue-500/15 text-blue-300">
              <Sparkles size={12} />
            </span>

            <span className="text-xs font-medium tracking-wide text-blue-300">
              A new way to build your professional world
            </span>
          </div>

          {/* Heading */}
          <h1 className="text-[clamp(3.4rem,6vw,6.3rem)] font-bold leading-[0.94] tracking-[-0.055em] text-white">
            Your professional
            <span className="block">world,</span>

            <span className="block bg-gradient-to-r from-blue-400 via-cyan-300 to-blue-500 bg-clip-text text-transparent">
              connected.
            </span>
          </h1>

          {/* Description */}
          <p className="mt-7 max-w-xl text-base leading-7 text-slate-400 sm:text-lg sm:leading-8">
            Build meaningful professional relationships,
            discover new opportunities, and grow together
            in one connected sphere.
          </p>

          {/* Actions */}
          <div className="mt-9 flex flex-col gap-3 sm:flex-row">
            <Link
              to="/auth/register"
              className="group inline-flex h-12 items-center justify-center gap-2 rounded-xl bg-white px-6 text-sm font-semibold text-slate-950 shadow-xl shadow-white/[0.05] transition duration-200 hover:-translate-y-0.5 hover:bg-slate-100"
            >
              Start building your sphere

              <ArrowRight
                size={16}
                className="transition-transform duration-200 group-hover:translate-x-0.5"
              />
            </Link>

            <a
              href="#how-it-works"
              className="inline-flex h-12 items-center justify-center rounded-xl border border-white/10 bg-white/[0.02] px-6 text-sm font-medium text-slate-300 transition duration-200 hover:border-white/15 hover:bg-white/[0.05] hover:text-white"
            >
              Explore how it works
            </a>
          </div>

          {/* Core values */}
          <div className="mt-12 flex max-w-lg border-t border-white/[0.08] pt-6">
            <Value
              title="Connect"
              description="People who matter"
            />

            <Value
              title="Discover"
              description="Ideas & opportunities"
              bordered
            />

            <Value
              title="Grow"
              description="Together"
            />
          </div>
        </div>

        {/* Right visual */}
        <div className="relative flex min-h-[480px] items-center justify-center lg:min-h-[620px]">
          {/* Outer atmosphere */}
          <div className="pointer-events-none absolute h-[520px] w-[520px] rounded-full bg-blue-500/[0.04] blur-3xl" />

          {/* Orbit */}
          <div className="pointer-events-none absolute h-[430px] w-[430px] rounded-full border border-blue-400/[0.08] rotate-[-18deg]" />

          <div className="pointer-events-none absolute h-[360px] w-[500px] rounded-[50%] border border-cyan-400/[0.06] rotate-[25deg]" />

          {/* Decorative nodes */}
          <NetworkNode className="left-[8%] top-[27%]" />
          <NetworkNode className="right-[10%] top-[21%]" />
          <NetworkNode className="left-[18%] bottom-[24%]" />
          <NetworkNode className="right-[18%] bottom-[19%]" />

          {/* Connecting lines */}
          <div className="pointer-events-none absolute left-[11%] top-[31%] h-px w-[28%] rotate-[20deg] bg-gradient-to-r from-transparent via-blue-400/20 to-transparent" />

          <div className="pointer-events-none absolute right-[12%] top-[28%] h-px w-[27%] -rotate-[22deg] bg-gradient-to-r from-transparent via-cyan-400/20 to-transparent" />

          {/* Image atmosphere */}
          <div className="absolute h-[430px] w-[600px] rounded-full bg-blue-500/[0.08] blur-[90px]" />

          {/* Hero image */}
          <img
            src={HERO_IMAGE}
            alt="Connected professionals around the LinkedSphere network"
            className="relative z-10 w-[115%] max-w-[720px] object-contain mix-blend-screen drop-shadow-[0_0_45px_rgba(37,99,235,0.18)] sm:w-[110%] lg:w-[120%]"
          />

          {/* Small floating label */}
          <div className="absolute bottom-[12%] left-[8%] z-20 hidden rounded-xl border border-white/10 bg-slate-950/70 px-4 py-3 shadow-xl backdrop-blur-md sm:block">
            <div className="flex items-center gap-2">
              <span className="h-2 w-2 rounded-full bg-cyan-400 shadow-[0_0_10px_rgba(34,211,238,0.8)]" />

              <span className="text-xs font-medium text-slate-300">
                Your sphere is growing
              </span>
            </div>
          </div>

          {/* Small floating label */}
          <div className="absolute right-[4%] top-[18%] z-20 hidden rounded-xl border border-white/10 bg-slate-950/70 px-4 py-3 shadow-xl backdrop-blur-md sm:block">
            <p className="text-[10px] uppercase tracking-wider text-slate-500">
              Network
            </p>

            <p className="mt-1 text-sm font-semibold text-white">
              Connected
            </p>
          </div>
        </div>
      </div>

      {/* Bottom scroll indicator */}
      <div className="absolute bottom-6 left-1/2 hidden -translate-x-1/2 flex-col items-center gap-2 text-slate-600 lg:flex">
        <span className="text-[9px] uppercase tracking-[0.3em]">
          Explore
        </span>

        <div className="h-8 w-px bg-gradient-to-b from-slate-600 to-transparent" />
      </div>
    </section>
  );
}

function Value({
  title,
  description,
  bordered = false,
}: {
  title: string;
  description: string;
  bordered?: boolean;
}) {
  return (
    <div
      className={`flex-1 px-4 first:pl-0 ${
        bordered ? "border-x border-white/[0.08]" : ""
      }`}
    >
      <p className="text-sm font-semibold text-white">
        {title}
      </p>

      <p className="mt-1 text-[11px] leading-5 text-slate-500">
        {description}
      </p>
    </div>
  );
}

function NetworkNode({
  className,
}: {
  className: string;
}) {
  return (
    <div
      className={`absolute z-20 h-2.5 w-2.5 rounded-full bg-blue-400 shadow-[0_0_18px_rgba(59,130,246,0.9)] ${className}`}
    >
      <span className="absolute inset-[-5px] animate-ping rounded-full bg-blue-400/20" />
    </div>
  );
}

export default HeroSection;