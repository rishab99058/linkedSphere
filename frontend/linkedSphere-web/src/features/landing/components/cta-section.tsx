import { ArrowRight } from "lucide-react";
import { Link } from "react-router-dom";

function CtaSection() {
  return (
    <section className="relative overflow-hidden bg-[#020617] py-24 sm:py-28 lg:py-32">
      {/* Ambient background glow */}
      <div className="pointer-events-none absolute left-1/2 top-1/2 h-[600px] w-[600px] -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-500/10 blur-[140px]" />

      <div className="relative mx-auto max-w-7xl px-6 lg:px-8">
        <div className="relative overflow-hidden rounded-[2.5rem] border border-white/10 bg-slate-950">
          {/* Network visual */}
          <div className="pointer-events-none absolute inset-0">
            <img
              src="https://res.cloudinary.com/dws1oujlk/image/upload/v1788706425/1_zm6cid.jpg"
              alt=""
              className="h-full w-full object-cover opacity-20"
            />

            <div className="absolute inset-0 bg-[#020617]/80" />

            <div className="absolute inset-0 bg-[radial-gradient(circle_at_center,rgba(59,130,246,0.16),transparent_55%)]" />
          </div>

          {/* Content */}
          <div className="relative flex min-h-[520px] flex-col items-center justify-center px-6 py-20 text-center sm:px-10 lg:min-h-[560px]">
            <div className="mb-7 flex items-center gap-3">
              <span className="h-px w-10 bg-cyan-400/60" />

              <p className="text-sm font-semibold uppercase tracking-[0.2em] text-cyan-400">
                Start your journey
              </p>

              <span className="h-px w-10 bg-cyan-400/60" />
            </div>

            <h2 className="max-w-4xl text-4xl font-bold tracking-tight text-white sm:text-5xl lg:text-6xl lg:leading-[1.05]">
              The next opportunity could be
              <span className="block text-blue-400">one connection away.</span>
            </h2>

            <p className="mx-auto mt-7 max-w-2xl text-base leading-7 text-slate-400 sm:text-lg sm:leading-8">
              Create your professional identity, discover your sphere and
              connect with people who can help shape what comes next.
            </p>

            <Link
              to="/auth/register"
              className="group mt-9 inline-flex items-center gap-3 rounded-full bg-white px-7 py-4 text-sm font-semibold text-slate-950 transition-all duration-300 hover:-translate-y-0.5 hover:bg-slate-100 hover:shadow-[0_0_35px_rgba(255,255,255,0.12)]"
            >
              Join LinkedSphere

              <span className="flex h-7 w-7 items-center justify-center rounded-full bg-slate-950 text-white transition-transform duration-300 group-hover:translate-x-1">
                <ArrowRight size={15} />
              </span>
            </Link>

            <p className="mt-6 text-sm text-slate-500">
              Build your sphere. Discover what's next.
            </p>
          </div>

          {/* Decorative orbit */}
          <div className="pointer-events-none absolute -right-20 -top-20 h-52 w-52 rounded-full border border-blue-400/10" />

          <div className="pointer-events-none absolute -bottom-24 -left-16 h-64 w-64 rounded-full border border-cyan-400/10" />
        </div>
      </div>
    </section>
  );
}

export default CtaSection;