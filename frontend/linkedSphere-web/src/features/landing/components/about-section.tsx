import { ArrowUpRight, Lightbulb, Rocket, Users } from "lucide-react";
import type { ReactNode } from "react";

const ABOUT_GLOBE =
  "https://res.cloudinary.com/up1blk1m/image/upload/v1788802250/About.png";

const PILLARS = [
  {
    icon: Users,
    number: "01",
    title: "Connect",
    text: "Build relationships with people who share your professional interests and ambitions.",
  },
  {
    icon: Lightbulb,
    number: "02",
    title: "Discover",
    text: "Find people, ideas and opportunities that move your professional journey forward.",
  },
  {
    icon: Rocket,
    number: "03",
    title: "Grow",
    text: "Turn meaningful connections into conversations, collaboration and lasting growth.",
  },
];

function AboutSection() {
  return (
    <section id="about" className="relative overflow-hidden bg-white py-20 sm:py-24 lg:py-28">
      {/* Background atmosphere */}
      <div className="pointer-events-none absolute -right-48 top-1/4 h-[32rem] w-[32rem] rounded-full bg-blue-100/40 blur-[100px]" />
      <div className="pointer-events-none absolute -left-48 bottom-0 h-80 w-80 rounded-full bg-cyan-100/30 blur-[90px]" />

      <div className="relative mx-auto max-w-7xl px-6 lg:px-8">
        <div className="grid items-center gap-12 lg:grid-cols-[1fr_0.95fr] lg:gap-16 xl:gap-24">
          {/* Content */}
          <div className="max-w-2xl">
            <div className="inline-flex items-center gap-2.5 rounded-full border border-blue-100 bg-blue-50/70 px-3.5 py-2">
              <span className="relative flex h-2 w-2">
                <span className="absolute inline-flex h-full w-full animate-ping rounded-full bg-blue-400 opacity-60" />
                <span className="relative inline-flex h-2 w-2 rounded-full bg-blue-600" />
              </span>

              <span className="text-[11px] font-semibold uppercase tracking-[0.18em] text-blue-600">
                About LinkedSphere
              </span>
            </div>

            <h2 className="mt-6 max-w-2xl text-4xl font-bold leading-[1.05] tracking-[-0.035em] text-slate-950 sm:text-5xl lg:text-[3.5rem]">
              A professional network built around{" "}
              <span className="text-blue-600">people.</span>
            </h2>

            <p className="mt-6 max-w-xl text-base leading-7 text-slate-600 sm:text-lg sm:leading-8">
              LinkedSphere brings your professional identity, relationships,
              discovery and opportunities into one connected experience.
            </p>

            <p className="mt-4 max-w-xl text-sm leading-7 text-slate-500 sm:text-base">
              Because a professional network should be more than a collection
              of profiles. It should help you discover the right people, start
              meaningful conversations and move forward together.
            </p>

            {/* Pillars */}
            <div className="mt-9 border-y border-slate-200">
              {PILLARS.map((pillar) => (
                <Pillar
                  key={pillar.number}
                  icon={<pillar.icon size={18} strokeWidth={2} />}
                  number={pillar.number}
                  title={pillar.title}
                  text={pillar.text}
                />
              ))}
            </div>
          </div>

          {/* Visual composition */}
          <div className="relative mx-auto flex h-[390px] w-full max-w-[520px] items-center justify-center sm:h-[500px] lg:h-[540px]">
            {/* Soft atmospheric glow */}
            <div className="absolute left-1/2 top-1/2 h-56 w-56 -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-400/20 blur-[70px] sm:h-72 sm:w-72" />

            {/* Fine orbit */}
            <div className="absolute left-1/2 top-1/2 h-[280px] w-[280px] -translate-x-1/2 -translate-y-1/2 rounded-full border border-blue-200/60 sm:h-[370px] sm:w-[370px]" />

            <div className="absolute left-1/2 top-1/2 h-[340px] w-[210px] -translate-x-1/2 -translate-y-1/2 rotate-[28deg] rounded-[50%] border border-cyan-200/50 sm:h-[450px] sm:w-[280px]" />

            <div className="absolute left-1/2 top-1/2 h-[210px] w-[340px] -translate-x-1/2 -translate-y-1/2 -rotate-[18deg] rounded-[50%] border border-blue-200/40 sm:h-[280px] sm:w-[450px]" />

            {/* Orbit nodes */}
            <span className="absolute left-[13%] top-[30%] h-2 w-2 rounded-full bg-blue-500 shadow-[0_0_18px_rgba(59,130,246,0.7)]" />
            <span className="absolute right-[13%] top-[23%] h-1.5 w-1.5 rounded-full bg-cyan-400 shadow-[0_0_15px_rgba(34,211,238,0.7)]" />
            <span className="absolute bottom-[24%] right-[18%] h-2 w-2 rounded-full bg-blue-500 shadow-[0_0_18px_rgba(59,130,246,0.7)]" />

            {/* Globe */}
            <div className="relative z-10 w-[270px] sm:w-[350px] lg:w-[410px]">
              <img
                src={ABOUT_GLOBE}
                alt="LinkedSphere connected professional network"
                className="h-auto w-full object-contain"
              />
            </div>

            {/* Floating information */}
            <div className="absolute left-0 top-[16%] z-20 hidden rounded-2xl border border-slate-200/80 bg-white/90 px-3.5 py-3 shadow-[0_15px_40px_rgba(15,23,42,0.08)] backdrop-blur-md sm:block">
              <div className="flex items-center gap-2.5">
                <span className="flex h-8 w-8 items-center justify-center rounded-xl bg-blue-50 text-blue-600">
                  <Users size={15} />
                </span>

                <div>
                  <p className="text-[11px] font-semibold text-slate-900">
                    Your network
                  </p>
                  <p className="text-[10px] text-slate-500">
                    Meaningful connections
                  </p>
                </div>
              </div>
            </div>

            <div className="absolute bottom-[14%] right-0 z-20 hidden rounded-2xl border border-slate-200/80 bg-white/90 px-3.5 py-3 shadow-[0_15px_40px_rgba(15,23,42,0.08)] backdrop-blur-md sm:block">
              <div className="flex items-center gap-2.5">
                <span className="flex h-8 w-8 items-center justify-center rounded-xl bg-cyan-50 text-cyan-600">
                  <ArrowUpRight size={15} />
                </span>

                <div>
                  <p className="text-[11px] font-semibold text-slate-900">
                    Keep growing
                  </p>
                  <p className="text-[10px] text-slate-500">
                    One connection at a time
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Closing statement */}
        <div className="mt-14 flex flex-col gap-3 border-t border-slate-200 pt-7 sm:flex-row sm:items-center sm:justify-between">
          <p className="max-w-xl text-sm leading-6 text-slate-500">
            Built for professionals who believe the right connection can
            change what comes next.
          </p>

          <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
            <span>Connect. Discover. Grow.</span>
            <ArrowUpRight size={16} className="text-blue-600" />
          </div>
        </div>
      </div>
    </section>
  );
}

function Pillar({
  icon,
  number,
  title,
  text,
}: {
  icon: ReactNode;
  number: string;
  title: string;
  text: string;
}) {
  return (
    <article className="group flex items-center gap-4 border-b border-slate-200 py-4 last:border-b-0 sm:gap-5">
      <span className="w-6 shrink-0 text-[10px] font-bold tracking-wider text-slate-400">
        {number}
      </span>

      <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-xl bg-slate-50 text-blue-600 transition-colors duration-200 group-hover:bg-blue-50">
        {icon}
      </div>

      <div className="min-w-0">
        <h3 className="text-sm font-semibold text-slate-950">{title}</h3>
        <p className="mt-0.5 text-xs leading-5 text-slate-500 sm:text-sm">
          {text}
        </p>
      </div>
    </article>
  );
}

export default AboutSection;