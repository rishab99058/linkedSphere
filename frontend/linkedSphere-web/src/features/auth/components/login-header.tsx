function LoginHeader() {
  return (
    <div className="space-y-4">
      <div className="flex items-center gap-2">
        <span className="h-1.5 w-1.5 rounded-full bg-blue-600 shadow-[0_0_8px_rgba(37,99,235,0.55)]" />

        <span className="text-xs font-semibold uppercase tracking-[0.16em] text-blue-700">
          Welcome back
        </span>
      </div>

      <div>
        <h1 className="text-[2rem] font-bold leading-tight tracking-[-0.025em] text-slate-950 sm:text-3xl">
          Sign in to LinkedSphere
        </h1>

        <p className="mt-3 max-w-sm text-sm leading-6 text-slate-500">
          Continue building your professional network.
        </p>
      </div>
    </div>
  );
}

export default LoginHeader;