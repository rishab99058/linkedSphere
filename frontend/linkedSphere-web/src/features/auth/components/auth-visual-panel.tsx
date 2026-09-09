import React from "react";
import {
  Lightbulb,
  Rocket,
  Users,
} from "lucide-react";
import { Link } from "react-router-dom";

const LINKEDSPHERE_LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

const LINKEDSPHERE_NETWORK_GIF =
  "https://res.cloudinary.com/up1blk1m/image/upload/v1788870119/LinkedSphere_animated_network.gif";

function AuthVisualPanel() {
  return (
    <section className="relative hidden h-dvh w-[53%] overflow-hidden lg:block">
      {/* Animated network background */}
      <img
        src={LINKEDSPHERE_NETWORK_GIF}
        alt=""
        aria-hidden="true"
        className="absolute inset-0 h-full w-full object-cover opacity-45"
      />

      {/* Dark readability overlay */}
      <div className="absolute inset-0 bg-[#020617]/70" />

      {/* Center focus */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_center,transparent_18%,rgba(2,6,23,0.35)_55%,rgba(2,6,23,0.88)_100%)]" />

      {/* Ambient light */}
      <div className="absolute left-[-18%] top-[-12%] h-[55%] w-[55%] rounded-full bg-blue-600/[0.10] blur-[120px]" />

      <div className="absolute bottom-[-20%] right-[-12%] h-[60%] w-[60%] rounded-full bg-cyan-500/[0.07] blur-[130px]" />

      <div className="relative z-10 flex h-full flex-col px-10 py-8 xl:px-14">
        {/* Brand */}
        <Link
          to="/"
          aria-label="Go to LinkedSphere home"
          className="group flex w-fit shrink-0 items-center gap-3 rounded-xl p-1.5 transition duration-200 hover:bg-white/[0.04] focus:outline-none focus:ring-2 focus:ring-blue-400/50"
        >
          <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-blue-500/10 ring-1 ring-blue-400/20 transition duration-200 group-hover:bg-blue-500/15">
            <img
              src={LINKEDSPHERE_LOGO}
              alt="LinkedSphere"
              className="h-9 w-9 object-contain"
            />
          </div>

          <div>
            <p className="text-base font-bold tracking-[-0.01em] text-white">
              LinkedSphere
            </p>

            <p className="mt-0.5 text-[10px] font-medium tracking-wide text-slate-500">
              Your professional network
            </p>
          </div>
        </Link>

        {/* Main message */}
        <div className="mt-[clamp(3rem,8vh,6rem)] shrink-0">
          <div className="flex items-center gap-2">
            <span className="h-1.5 w-1.5 rounded-full bg-blue-400 shadow-[0_0_10px_rgba(96,165,250,0.8)]" />

            <span className="text-[10px] font-semibold uppercase tracking-[0.24em] text-blue-400">
              The professional network
            </span>
          </div>

          <h1 className="mt-4 max-w-2xl text-[clamp(2.8rem,4.2vw,4.5rem)] font-bold leading-[0.96] tracking-[-0.05em] text-white">
            Connect with the people
            <span className="block">
              shaping your future.
            </span>
          </h1>

          <div className="mt-4 flex items-center gap-2">
            <span className="text-sm font-semibold text-blue-300">
              Build.
            </span>

            <span className="text-slate-600">/</span>

            <span className="text-sm font-semibold text-cyan-300">
              Discover.
            </span>

            <span className="text-slate-600">/</span>

            <span className="text-sm font-semibold text-blue-300">
              Grow.
            </span>
          </div>

          <p className="mt-5 max-w-lg text-[14px] leading-6 text-slate-300/90">
            A professional space to build meaningful connections, share ideas,
            discover opportunities, and move forward together.
          </p>
        </div>

        {/* Core pillars */}
        <div className="mt-9 grid max-w-xl grid-cols-3">
          <Pillar
            icon={<Users />}
            title="Connect"
            description="Build meaningful relationships."
          />

          <Pillar
            icon={<Lightbulb />}
            title="Discover"
            description="Find ideas and opportunities."
            bordered
          />

          <Pillar
            icon={<Rocket />}
            title="Grow"
            description="Move your career forward."
          />
        </div>

        {/* Visual breathing space */}
        <div className="min-h-0 flex-1" />

        {/* Bottom stats */}
        <div className="grid shrink-0 grid-cols-3 border-t border-white/[0.08] pt-4">
          <Stat
            value="10K+"
            label="Professionals"
          />

          <Stat
            value="100+"
            label="Opportunities"
            bordered
          />

          <Stat
            value="∞"
            label="Possibilities"
          />
        </div>
      </div>
    </section>
  );
}

function Pillar({
  icon,
  title,
  description,
  bordered = false,
}: {
  icon: React.ReactNode;
  title: string;
  description: string;
  bordered?: boolean;
}) {
  return (
    <div
      className={`px-4 ${
        bordered ? "border-x border-white/[0.08]" : ""
      }`}
    >
      <div className="mb-2.5 flex h-8 w-8 items-center justify-center rounded-lg bg-white/[0.05] text-blue-300 ring-1 ring-white/[0.08]">
        {React.cloneElement(
          icon as React.ReactElement,
          {
            size: 16,
          },
        )}
      </div>

      <p className="text-xs font-semibold text-white">
        {title}
      </p>

      <p className="mt-1 max-w-[125px] text-[10px] leading-4 text-slate-500">
        {description}
      </p>
    </div>
  );
}

function Stat({
  value,
  label,
  bordered = false,
}: {
  value: string;
  label: string;
  bordered?: boolean;
}) {
  return (
    <div
      className={`text-center ${
        bordered ? "border-x border-white/[0.08]" : ""
      }`}
    >
      <p className="text-lg font-bold tracking-[-0.02em] text-white">
        {value}
      </p>

      <p className="mt-0.5 text-[10px] font-medium text-slate-500">
        {label}
      </p>
    </div>
  );
}

export default AuthVisualPanel;