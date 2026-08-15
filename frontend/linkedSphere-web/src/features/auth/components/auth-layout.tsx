import type { ReactNode } from "react";

interface AuthLayoutProps {
  children: ReactNode;
}

function AuthLayout({ children }: AuthLayoutProps) {
  return (
    <main className="flex min-h-screen items-center justify-center bg-slate-100">
      {children}
    </main>
  );
}

export default AuthLayout;