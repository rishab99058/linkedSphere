import {
  ArrowUpRight,
  BriefcaseBusiness,
  MessageCircle,
  Network,
  UserSearch,
} from "lucide-react";

const FEATURES_IMAGE =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786861410/ChatGPT_Image_Aug_16_2026_11_43_27_AM_ijecjf.png";

const FEATURES = [
  {
    icon: Network,
    title: "Build your network",
    text: "Create professional relationships that are relevant to your journey.",
  },
  {
    icon: UserSearch,
    title: "Discover people",
    text: "Find professionals, skills and opportunities aligned with where you want to go.",
  },
  {
    icon: MessageCircle,
    title: "Share your voice",
    text: "Publish ideas, experiences and conversations that create meaningful engagement.",
  },
  {
    icon: BriefcaseBusiness,
    title: "Create opportunities",
    text: "Turn connections and conversations into collaboration and new possibilities.",
  },
];

function FeaturesSection() {
  return (
    <section
      id="features"
      className="relative overflow-hidden bg-white py-20 sm:py-24 lg:py-28"
    >
      {/* Background atmosphere */}
      <div className="pointer-events-none absolute -right-56 top-1/3 h-[34rem] w-[34rem] rounded-full bg-blue-100/40 blur-[120px]" />

      <div className="pointer-events-none absolute -left-48 bottom-0 h-80 w-80 rounded-full bg-cyan-100/25 blur-[100px]" />

      <div className="relative mx-auto max-w-7xl px-6 lg:px-8">
        {/* Header */}
        <div className="grid items-end gap-8 lg:grid-cols-[1fr_0.7fr]">
          <div>
            <div className="inline-flex items-center gap-2.5 rounded-full border border-blue-100 bg-blue-50/70 px-3.5 py-2">
              <span className="h-1.5 w-1.5 rounded-full bg-blue-600" />

              <span className="text-[11px] font-semibold uppercase tracking-[0.18em] text-blue-600">
                Features
              </span>
            </div>

            <h2 className="mt-6 max-w-2xl text-4xl font-bold leading-[1.05] tracking-[-0.035em] text-slate-950 sm:text-5xl lg:text-[3.5rem]">
              Everything you need to{" "}
              <span className="text-blue-600">move forward.</span>
            </h2>
          </div>

          <p className="max-w-md text-base leading-7 text-slate-500 sm:text-lg sm:leading-8 lg:pb-1">
            LinkedSphere brings the essential parts of professional networking
            together — without making the experience complicated.
          </p>
        </div>

        {/* Main feature composition */}
        <div className="mt-16 grid items-center gap-12 lg:grid-cols-[0.95fr_1.05fr] lg:gap-20">
          {/* Feature list */}
          <div className="border-y border-slate-200">
            {FEATURES.map((feature) => {
              const Icon = feature.icon;

              return (
                <article
                  key={feature.title}
                  className="group flex gap-5 border-b border-slate-200 py-6 last:border-b-0"
                >
                  <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-xl border border-slate-200 bg-slate-50 text-slate-700 transition-all duration-300 group-hover:border-blue-200 group-hover:bg-blue-50 group-hover:text-blue-600">
                    <Icon size={19} strokeWidth={1.8} />
                  </div>

                  <div className="min-w-0">
                    <div className="flex items-center gap-2">
                      <h3 className="text-base font-semibold text-slate-950 sm:text-lg">
                        {feature.title}
                      </h3>

                      <ArrowUpRight
                        size={15}
                        className="text-slate-300 transition-all duration-300 group-hover:-translate-y-0.5 group-hover:translate-x-0.5 group-hover:text-blue-500"
                      />
                    </div>

                    <p className="mt-1.5 max-w-lg text-sm leading-6 text-slate-500">
                      {feature.text}
                    </p>
                  </div>
                </article>
              );
            })}
          </div>

          {/* Visual */}
          <div className="relative flex min-h-[420px] items-center justify-center sm:min-h-[500px]">
            {/* Ambient glow */}
            <div className="absolute left-1/2 top-1/2 h-72 w-72 -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-400/20 blur-[90px] sm:h-96 sm:w-96" />

            {/* Decorative rings */}
            <div className="absolute h-[300px] w-[300px] rounded-full border border-blue-100 sm:h-[410px] sm:w-[410px]" />

            <div className="absolute h-[360px] w-[210px] rotate-[25deg] rounded-[50%] border border-cyan-100 sm:h-[470px] sm:w-[270px]" />

            {/* Image */}
            <div className="relative z-10 w-[310px] sm:w-[400px] lg:w-[470px]">
              <img
                src={FEATURES_IMAGE}
                alt="LinkedSphere professional networking experience"
                className="h-auto w-full object-contain drop-shadow-[0_25px_50px_rgba(37,99,235,0.18)]"
              />
            </div>

            {/* Small floating label */}
            <div className="absolute bottom-[10%] left-0 z-20 hidden rounded-2xl border border-slate-200 bg-white/90 px-4 py-3 shadow-xl shadow-slate-900/5 backdrop-blur-md sm:block lg:left-2">
              <div className="flex items-center gap-3">
                <span className="flex h-8 w-8 items-center justify-center rounded-xl bg-blue-50 text-blue-600">
                  <Network size={15} />
                </span>

                <div>
                  <p className="text-[9px] font-bold uppercase tracking-[0.15em] text-blue-600">
                    Your professional world
                  </p>

                  <p className="mt-0.5 text-xs font-semibold text-slate-900">
                    Connected in one sphere
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Closing statement */}
        <div className="mt-16 border-t border-slate-200 pt-8">
          <div className="flex flex-col gap-5 sm:flex-row sm:items-center sm:justify-between">
            <div>
              <p className="text-sm font-semibold text-slate-950">
                Built around meaningful professional relationships.
              </p>

              <p className="mt-1 text-sm text-slate-500">
                Simple tools. Better connections. More possibilities.
              </p>
            </div>

            <div className="flex items-center gap-3">
              <span className="h-px w-10 bg-blue-200" />

              <span className="text-xs font-bold uppercase tracking-[0.2em] text-blue-600">
                Connect · Discover · Grow
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default FeaturesSection;