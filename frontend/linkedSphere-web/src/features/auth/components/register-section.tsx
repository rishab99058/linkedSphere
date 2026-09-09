import { ArrowRight } from "lucide-react";
import { Link } from "react-router-dom";

function RegisterSection() {
  return (
    <div className="relative pt-2">
      {/* Divider */}
      <div className="mb-5 flex items-center gap-3">
        <div className="h-px flex-1 bg-slate-200" />

        <span className="text-[11px] font-medium uppercase tracking-[0.14em] text-slate-400">
          New here?
        </span>

        <div className="h-px flex-1 bg-slate-200" />
      </div>

      {/* Register CTA */}
      <div className="flex items-center justify-center gap-1.5 text-sm">
        <span className="text-slate-500">
          Don't have a LinkedSphere account?
        </span>

        <Link
          to="/auth/register"
          className="group inline-flex items-center gap-1 font-semibold text-blue-600 transition-colors duration-200 hover:text-blue-700"
        >
          Create one
          <ArrowRight
            size={14}
            className="transition-transform duration-200 group-hover:translate-x-0.5"
          />
        </Link>
      </div>
    </div>
  );
}

export default RegisterSection;