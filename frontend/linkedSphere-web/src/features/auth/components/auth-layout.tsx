import type { ReactNode } from "react";

import AuthNetworkBackground from "./auth-network-background";
import AuthVisualPanel from "./auth-visual-panel";

interface AuthLayoutProps {
  children: ReactNode;
}

function AuthLayout({ children }: AuthLayoutProps) {
  return (
    <main className="relative min-h-dvh overflow-hidden bg-[#020617]">
      <AuthNetworkBackground />

      <div className="relative z-10 flex min-h-dvh w-full">
        <AuthVisualPanel />

        <section className="flex min-h-dvh w-full items-center justify-center overflow-y-auto px-5 py-5 lg:w-[47%] lg:px-8 xl:px-12">
          <div className="w-full max-w-md">
            {children}
          </div>
        </section>
      </div>
    </main>
  );
}

export default AuthLayout;