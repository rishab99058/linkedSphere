import {
  ArrowRight,
  Sparkles,
} from "lucide-react";
import { Link } from "react-router-dom";

function CtaSection() {
  return (
    <section className="px-5 py-24 sm:px-8 lg:px-10 lg:py-32">
      <div className="relative mx-auto max-w-6xl overflow-hidden rounded-[2rem] bg-slate-950 px-6 py-20 text-center sm:px-12">
        {/* Glows */}
        <div className="absolute left-1/2 top-[-120px] h-80 w-80 -translate-x-1/2 rounded-full bg-blue-600/20 blur-[100px]" />

        <div className="absolute bottom-[-150px] left-[20%] h-72 w-72 rounded-full bg-cyan-500/10 blur-[100px]" />

        <div className="relative">
          <div className="mx-auto flex w-fit items-center gap-2 rounded-full border border-blue-400/20 bg-blue-400/10 px-4 py-2 text-sm font-medium text-blue-300">
            <Sparkles className="h-4 w-4" />
            Your sphere starts here
          </div>

          <h2 className="mx-auto mt-7 max-w-3xl text-4xl font-bold tracking-tight text-white sm:text-5xl lg:text-6xl">
            Ready to build your
            <span className="text-blue-400">
              {" "}
              sphere?
            </span>
          </h2>

          <p className="mx-auto mt-6 max-w-xl text-base leading-7 text-slate-400 sm:text-lg">
            Start connecting with people, sharing ideas, and discovering new
            possibilities.
          </p>

          <div className="mt-9 flex flex-col justify-center gap-3 sm:flex-row">
            <Link
              to="/auth/register"
              className="group inline-flex items-center justify-center gap-2 rounded-xl bg-blue-600 px-6 py-3.5 text-sm font-semibold text-white shadow-xl shadow-blue-600/20 transition-all hover:bg-blue-500"
            >
              Create your account

              <ArrowRight className="h-4 w-4 transition-transform group-hover:translate-x-1" />
            </Link>

            <Link
              to="/auth/login"
              className="inline-flex items-center justify-center rounded-xl border border-white/10 bg-white/5 px-6 py-3.5 text-sm font-semibold text-white transition-colors hover:bg-white/10"
            >
              Sign in
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
}

export default CtaSection;