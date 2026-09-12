function AuthNetworkBackground() {
  return (
    <div className="pointer-events-none fixed inset-0 z-0 overflow-hidden">
      {/* Main blue atmospheric glow */}
      <div className="absolute left-1/2 top-1/2 h-[70vh] w-[70vh] -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-600/[0.08] blur-[140px]" />

      {/* Secondary glows */}
      <div className="absolute -left-[15%] -top-[20%] h-[60vh] w-[60vh] rounded-full bg-blue-500/[0.07] blur-[130px]" />

      <div className="absolute -bottom-[20%] -right-[15%] h-[60vh] w-[60vh] rounded-full bg-cyan-500/[0.06] blur-[130px]" />

      {/* Network artwork */}
      <svg
        viewBox="0 0 1600 900"
        preserveAspectRatio="xMidYMid slice"
        className="absolute inset-0 h-full w-full opacity-[0.22]"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <defs>
          <radialGradient id="background-globe">
            <stop offset="0%" stopColor="#2563eb" stopOpacity="0.18" />
            <stop offset="60%" stopColor="#0f172a" stopOpacity="0.35" />
            <stop offset="100%" stopColor="#020617" stopOpacity="0" />
          </radialGradient>

          <linearGradient id="network-line">
            <stop offset="0%" stopColor="#3b82f6" stopOpacity="0" />
            <stop offset="50%" stopColor="#60a5fa" stopOpacity="0.8" />
            <stop offset="100%" stopColor="#22d3ee" stopOpacity="0" />
          </linearGradient>

          <filter id="background-glow">
            <feGaussianBlur stdDeviation="4" result="blur" />

            <feMerge>
              <feMergeNode in="blur" />
              <feMergeNode in="SourceGraphic" />
            </feMerge>
          </filter>

          <filter id="strong-glow">
            <feGaussianBlur stdDeviation="10" />
          </filter>
        </defs>

        {/* =====================================================
            LARGE ATMOSPHERIC SPHERE
        ===================================================== */}

        <circle
          cx="800"
          cy="500"
          r="430"
          fill="url(#background-globe)"
        />

        {/* =====================================================
            GLOBAL CONNECTION LINES
        ===================================================== */}

        <path
          d="M80 260 C360 100 530 210 800 470"
          stroke="url(#network-line)"
          strokeWidth="1.5"
        />

        <path
          d="M1520 250 C1260 130 1060 220 800 470"
          stroke="url(#network-line)"
          strokeWidth="1.5"
        />

        <path
          d="M60 690 C330 610 520 550 800 470"
          stroke="url(#network-line)"
          strokeWidth="1"
        />

        <path
          d="M1540 700 C1280 600 1050 560 800 470"
          stroke="url(#network-line)"
          strokeWidth="1"
        />

        <path
          d="M240 80 C520 300 610 380 800 470"
          stroke="url(#network-line)"
          strokeWidth="1"
        />

        <path
          d="M1370 80 C1110 290 1010 390 800 470"
          stroke="url(#network-line)"
          strokeWidth="1"
        />

        {/* =====================================================
            CENTRAL GLOBE
        ===================================================== */}

        <circle
          cx="800"
          cy="470"
          r="285"
          fill="#020617"
          fillOpacity="0.18"
          stroke="#3b82f6"
          strokeOpacity="0.18"
          strokeWidth="2"
        />

        {/* Outer orbit */}
        <ellipse
          cx="800"
          cy="470"
          rx="370"
          ry="125"
          transform="rotate(-15 800 470)"
          stroke="#3b82f6"
          strokeOpacity="0.15"
        />

        <ellipse
          cx="800"
          cy="470"
          rx="410"
          ry="160"
          transform="rotate(16 800 470)"
          stroke="#22d3ee"
          strokeOpacity="0.09"
        />

        {/* Globe latitude */}
        <ellipse
          cx="800"
          cy="470"
          rx="285"
          ry="90"
          stroke="#60a5fa"
          strokeOpacity="0.15"
        />

        <ellipse
          cx="800"
          cy="470"
          rx="285"
          ry="170"
          stroke="#60a5fa"
          strokeOpacity="0.09"
        />

        {/* Globe longitude */}
        <ellipse
          cx="800"
          cy="470"
          rx="90"
          ry="285"
          stroke="#22d3ee"
          strokeOpacity="0.13"
        />

        <ellipse
          cx="800"
          cy="470"
          rx="175"
          ry="285"
          stroke="#60a5fa"
          strokeOpacity="0.08"
        />

        <ellipse
          cx="800"
          cy="470"
          rx="235"
          ry="285"
          stroke="#60a5fa"
          strokeOpacity="0.05"
        />

        {/* =====================================================
            INTERNAL NETWORK
        ===================================================== */}

        <path
          d="M560 440 C660 380 715 430 800 470 C890 515 980 480 1040 425"
          stroke="#3b82f6"
          strokeOpacity="0.25"
        />

        <path
          d="M585 535 C680 475 740 490 800 470 C880 440 950 470 1010 540"
          stroke="#22d3ee"
          strokeOpacity="0.2"
        />

        <path
          d="M720 210 C740 320 770 395 800 470 C830 550 850 625 870 730"
          stroke="#60a5fa"
          strokeOpacity="0.12"
        />

        {/* =====================================================
            NETWORK NODES
        ===================================================== */}

        <BackgroundNode cx="585" cy="425" />

        <BackgroundNode cx="665" cy="350" />

        <BackgroundNode cx="750" cy="285" />

        <BackgroundNode cx="850" cy="325" />

        <BackgroundNode cx="955" cy="405" />

        <BackgroundNode cx="1000" cy="500" />

        <BackgroundNode cx="900" cy="570" />

        <BackgroundNode cx="790" cy="625" />

        <BackgroundNode cx="680" cy="550" />

        <BackgroundNode cx="800" cy="470" strong />

        {/* Outside nodes */}
        <BackgroundNode cx="230" cy="300" />

        <BackgroundNode cx="1380" cy="280" />

        <BackgroundNode cx="190" cy="680" />

        <BackgroundNode cx="1410" cy="690" />

        <BackgroundNode cx="420" cy="120" />

        <BackgroundNode cx="1190" cy="120" />

        {/* =====================================================
            EXTRA CONNECTING NETWORK
        ===================================================== */}

        <path
          d="M230 300 L420 120 L665 350"
          stroke="#3b82f6"
          strokeOpacity="0.1"
        />

        <path
          d="M1380 280 L1190 120 L955 405"
          stroke="#22d3ee"
          strokeOpacity="0.1"
        />

        <path
          d="M190 680 L420 610 L680 550"
          stroke="#3b82f6"
          strokeOpacity="0.08"
        />

        <path
          d="M1410 690 L1190 610 L900 570"
          stroke="#22d3ee"
          strokeOpacity="0.08"
        />
      </svg>

      {/* Dark readability layer */}
      <div className="absolute inset-0 bg-slate-950/55" />

      {/* Center focus */}
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_50%_52%,transparent_0%,rgba(2,6,23,0.15)_35%,rgba(2,6,23,0.75)_100%)]" />
    </div>
  );
}

function BackgroundNode({
  cx,
  cy,
  strong = false,
}: {
  cx: number | string;
  cy: number | string;
  strong?: boolean;
}) {
  return (
    <>
      {strong && (
        <circle
          cx={cx}
          cy={cy}
          r="25"
          fill="#22d3ee"
          fillOpacity="0.06"
        />
      )}

      <circle
        cx={cx}
        cy={cy}
        r={strong ? "5" : "3"}
        fill={strong ? "#22d3ee" : "#60a5fa"}
        filter="url(#background-glow)"
      />
    </>
  );
}

export default AuthNetworkBackground;