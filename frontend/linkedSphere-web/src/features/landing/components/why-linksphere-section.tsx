const reasons = [
  {
    title: "Meaningful connections",
    text: "Build relationships around shared professional interests, ambitions and ideas.",
  },
  {
    title: "Professional discovery",
    text: "Find people, perspectives and opportunities that can genuinely shape your journey.",
  },
  {
    title: "Growth in every direction",
    text: "Learn, contribute and grow inside a network where opportunity moves both ways.",
  },
];

function WhyLinkedSphereSection() {
  return (
    <section
      id="why-linksphere"
      className="relative overflow-hidden bg-white py-20 sm:py-24 lg:py-28"
    >
      <div className="mx-auto max-w-7xl px-6 lg:px-8">
        <div className="grid items-center gap-14 lg:grid-cols-[1fr_1.05fr] lg:gap-20">
          {/* Visual */}
          <div className="relative order-2 lg:order-1">
            <div className="absolute left-1/2 top-1/2 h-80 w-80 -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-500/10 blur-[100px]" />

            <div className="relative mx-auto max-w-xl">
              <img
                src="https://res.cloudinary.com/up1blk1m/image/upload/v1788860341/whyLSsection.png"
                alt="LinkedSphere professional network"
                className="relative h-auto w-full object-contain"
              />

              {/* Orbit details */}
              <div className="pointer-events-none absolute -left-4 top-1/4 h-12 w-12 rounded-full border border-blue-500/20" />

              <div className="pointer-events-none absolute -right-3 bottom-1/4 h-16 w-16 rounded-full border border-cyan-500/20" />
            </div>
          </div>

          {/* Content */}
          <div className="order-1 lg:order-2">
            <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-600">
              Why LinkedSphere
            </p>

            <h2 className="mt-5 max-w-2xl text-4xl font-bold tracking-tight text-slate-950 sm:text-5xl lg:text-[3.5rem] lg:leading-[1.08]">
              A professional network designed to move with you.
            </h2>

            <p className="mt-7 max-w-xl text-base leading-7 text-slate-600 sm:text-lg sm:leading-8">
              Your professional journey is constantly changing. Your network
              should be able to change with it.
            </p>

            <div className="mt-10">
              {reasons.map((reason, index) => (
                <article
                  key={reason.title}
                  className="border-t border-slate-200 py-6 last:border-b"
                >
                  <div className="flex gap-5">
                    <span className="mt-1 text-sm font-semibold text-blue-600">
                      0{index + 1}
                    </span>

                    <div>
                      <h3 className="text-xl font-semibold tracking-tight text-slate-950">
                        {reason.title}
                      </h3>

                      <p className="mt-2 max-w-lg leading-7 text-slate-600">
                        {reason.text}
                      </p>
                    </div>
                  </div>
                </article>
              ))}
            </div>

            <div className="mt-8 flex items-center gap-3">
              <span className="h-px w-10 bg-blue-600" />

              <p className="text-sm font-medium text-slate-500">
                Built for the way professional relationships actually grow.
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default WhyLinkedSphereSection;