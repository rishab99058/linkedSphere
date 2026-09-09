function NetworkSection() {
  return (
    <section className="relative overflow-hidden bg-[#020617] py-20 sm:py-24 lg:py-28">
      {/* Ambient glow */}
      <div className="pointer-events-none absolute left-1/2 top-1/2 h-[500px] w-[500px] -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-500/10 blur-[140px]" />

      <div className="relative mx-auto max-w-7xl px-6 lg:px-8">
        <div className="grid items-center gap-14 lg:grid-cols-[0.85fr_1.15fr] lg:gap-20">
          {/* Content */}
          <div className="max-w-xl">
            <p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-400">
              Your sphere
            </p>

            <h2 className="mt-5 text-4xl font-bold tracking-tight text-white sm:text-5xl lg:text-[3.5rem] lg:leading-[1.08]">
              Your network is bigger than your next connection.
            </h2>

            <p className="mt-7 text-base leading-7 text-slate-400 sm:text-lg sm:leading-8">
              The right opportunity can come from a conversation, a shared
              interest, or someone you have never met before.
            </p>

            <p className="mt-5 text-base leading-7 text-slate-400 sm:text-lg sm:leading-8">
              LinkedSphere brings those possibilities into one connected
              professional world — where people, ideas and opportunities move
              together.
            </p>

            <div className="mt-9 flex items-center gap-4">
              <div className="h-px w-12 bg-cyan-400/60" />

              <span className="text-sm font-medium text-slate-300">
                One network. Endless possibilities.
              </span>
            </div>
          </div>

          {/* Network visual */}
          <div className="relative">
            {/* Outer atmosphere */}
            <div className="absolute -inset-8 rounded-[3rem] bg-blue-500/5 blur-2xl" />

            <div className="relative overflow-hidden rounded-[2rem] border border-white/10 bg-slate-950/40 shadow-2xl shadow-blue-950/30">
              <img
                src="https://res.cloudinary.com/dws1oujlk/image/upload/v1788706425/1_zm6cid.jpg"
                alt="Professionals connected across the LinkedSphere network"
                className="h-auto w-full object-cover"
              />

              {/* Subtle readability overlay */}
              <div className="pointer-events-none absolute inset-0 bg-gradient-to-tr from-[#020617]/20 via-transparent to-cyan-400/5" />
            </div>

            {/* Orbit detail */}
            <div className="pointer-events-none absolute -right-5 -top-5 h-20 w-20 rounded-full border border-cyan-400/20" />

            <div className="pointer-events-none absolute -bottom-5 -left-5 h-16 w-16 rounded-full border border-blue-400/20" />
          </div>
        </div>

        {/* Bottom message */}
        <div className="mt-20 border-t border-white/10 pt-8">
          <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
            <p className="max-w-2xl text-lg font-medium text-slate-200">
              Because meaningful professional growth happens through people.
            </p>

            <div className="flex items-center gap-2 text-sm text-slate-500">
              <span className="h-2 w-2 rounded-full bg-cyan-400 shadow-[0_0_12px_rgba(34,211,238,0.7)]" />
              <span>Connected by LinkedSphere</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default NetworkSection;