import {
  ArrowRight,
  BarChart3,
  Globe2,
  MessageCircle,
  Users,
} from "lucide-react";

const PRODUCT_PREVIEW_IMAGE =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786861410/ChatGPT_Image_Aug_16_2026_11_46_26_AM_h7fngh.png";

const PRODUCT_POINTS = [
  {
    icon: Users,
    title: "Your professional identity",
    text: "Bring your experience, skills and story together in one place.",
  },
  {
    icon: MessageCircle,
    title: "Conversations that matter",
    text: "Share ideas and turn professional interactions into real conversations.",
  },
  {
    icon: BarChart3,
    title: "See your progress",
    text: "Understand how your network and professional presence are growing.",
  },
  {
    icon: Globe2,
    title: "One connected sphere",
    text: "Keep people, ideas and opportunities connected around your journey.",
  },
];

function ProductPreviewSection() {
  return (
    <section
      id="product-preview"
      className="relative overflow-hidden bg-slate-950 py-20 text-white sm:py-24 lg:py-28"
    >
      {/* Background atmosphere */}
      <div className="pointer-events-none absolute left-1/2 top-1/2 h-[38rem] w-[38rem] -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-600/10 blur-[130px]" />

      <div className="pointer-events-none absolute -right-40 top-0 h-96 w-96 rounded-full bg-cyan-500/10 blur-[120px]" />

      <div className="relative mx-auto max-w-7xl px-6 lg:px-8">
        {/* Header */}
        <div className="max-w-3xl">
          <div className="inline-flex items-center gap-2.5 rounded-full border border-white/10 bg-white/[0.04] px-3.5 py-2">
            <span className="h-1.5 w-1.5 rounded-full bg-blue-400 shadow-[0_0_10px_rgba(96,165,250,0.8)]" />

            <span className="text-[11px] font-semibold uppercase tracking-[0.18em] text-blue-300">
              Your professional world
            </span>
          </div>

          <h2 className="mt-6 max-w-3xl text-4xl font-bold leading-[1.05] tracking-[-0.035em] text-white sm:text-5xl lg:text-[3.5rem]">
            Everything comes together{" "}
            <span className="text-blue-400">in one sphere.</span>
          </h2>

          <p className="mt-5 max-w-2xl text-base leading-7 text-slate-400 sm:text-lg sm:leading-8">
            Your profile, conversations, connections and opportunities should
            feel like one connected experience — not a collection of separate
            tools.
          </p>
        </div>

        {/* Product composition */}
        <div className="mt-14 grid items-center gap-12 lg:grid-cols-[1.05fr_0.95fr] lg:gap-16">
          {/* Visual */}
          <div className="relative order-2 lg:order-1">
            {/* Glow */}
            <div className="absolute left-1/2 top-1/2 h-72 w-72 -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-500/15 blur-[90px] sm:h-96 sm:w-96" />

            {/* Fine orbital rings */}
            <div className="absolute left-1/2 top-1/2 h-[330px] w-[330px] -translate-x-1/2 -translate-y-1/2 rounded-full border border-blue-400/10 sm:h-[480px] sm:w-[480px]" />

            <div className="absolute left-1/2 top-1/2 h-[420px] w-[220px] -translate-x-1/2 -translate-y-1/2 rotate-[25deg] rounded-[50%] border border-cyan-400/10 sm:h-[560px] sm:w-[290px]" />

            {/* Image */}
            <div className="relative z-10 mx-auto w-full max-w-[600px]">
              <img
                src={PRODUCT_PREVIEW_IMAGE}
                alt="LinkedSphere professional workspace and network"
                className="h-auto w-full object-contain drop-shadow-[0_25px_70px_rgba(37,99,235,0.22)]"
              />
            </div>

            {/* Floating status */}
            <div className="absolute bottom-[9%] left-[4%] z-20 hidden rounded-2xl border border-white/10 bg-slate-900/80 px-4 py-3 shadow-2xl backdrop-blur-xl sm:block">
              <div className="flex items-center gap-3">
                <span className="relative flex h-8 w-8 items-center justify-center rounded-xl bg-blue-500/10 text-blue-300">
                  <span className="absolute h-2 w-2 rounded-full bg-blue-400 shadow-[0_0_10px_rgba(96,165,250,0.8)]" />
                </span>

                <div>
                  <p className="text-[9px] font-bold uppercase tracking-[0.16em] text-blue-300">
                    Network
                  </p>

                  <p className="mt-0.5 text-xs font-semibold text-white">
                    Always connected
                  </p>
                </div>
              </div>
            </div>
          </div>

          {/* Content */}
          <div className="order-1 lg:order-2">
            <div className="space-y-0 border-y border-white/10">
              {PRODUCT_POINTS.map((point) => {
                const Icon = point.icon;

                return (
                  <article
                    key={point.title}
                    className="group flex gap-4 border-b border-white/10 py-6 last:border-b-0"
                  >
                    <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-xl border border-white/10 bg-white/[0.04] text-blue-300 transition-colors duration-300 group-hover:border-blue-400/30 group-hover:bg-blue-500/10">
                      <Icon size={18} strokeWidth={1.8} />
                    </div>

                    <div>
                      <h3 className="text-base font-semibold text-white sm:text-lg">
                        {point.title}
                      </h3>

                      <p className="mt-1.5 max-w-md text-sm leading-6 text-slate-400">
                        {point.text}
                      </p>
                    </div>
                  </article>
                );
              })}
            </div>

            {/* CTA */}
            <div className="mt-8 flex items-center gap-4">
              <a
                href="#how-it-works"
                className="group inline-flex items-center gap-2 rounded-xl bg-white px-5 py-3 text-sm font-semibold text-slate-950 transition hover:bg-blue-50"
              >
                See how it works
                <ArrowRight
                  size={16}
                  className="transition-transform duration-200 group-hover:translate-x-0.5"
                />
              </a>

              <span className="text-xs text-slate-500">
                Built for meaningful connections
              </span>
            </div>
          </div>
        </div>

        {/* Bottom statement */}
        <div className="mt-16 border-t border-white/10 pt-7">
          <div className="flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
            <p className="max-w-xl text-sm leading-6 text-slate-500">
              A professional experience designed to keep your world connected.
            </p>

            <div className="flex items-center gap-3">
              <span className="h-px w-10 bg-blue-500/40" />

              <span className="text-[10px] font-bold uppercase tracking-[0.22em] text-blue-300">
                Identity · People · Opportunities
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default ProductPreviewSection;