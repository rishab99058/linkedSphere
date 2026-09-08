import {
  ArrowRight,
  Compass,
  MessageCircle,
  UserRound,
} from "lucide-react";
import { useState } from "react";

const STEPS = [
  {
    number: "01",
    title: "Create your identity",
    description:
      "Build a professional profile that tells your story, experience and ambitions.",
    image:
      "https://res.cloudinary.com/up1blk1m/image/upload/v1788803890/Step_1.png",
    icon: UserRound,
    label: "PROFILE",
  },
  {
    number: "02",
    title: "Discover your sphere",
    description:
      "Explore professionals and connect with people aligned with your journey.",
    image:
      "https://res.cloudinary.com/up1blk1m/image/upload/v1788803889/Step_2.png",
    icon: Compass,
    label: "DISCOVER",
  },
  {
    number: "03",
    title: "Grow together",
    description:
      "Share ideas, create conversations and turn your network into opportunities.",
    image:
      "https://res.cloudinary.com/up1blk1m/image/upload/v1788803889/Step_3.png",
    icon: MessageCircle,
    label: "GROW",
  },
];

function HowItWorksSection() {
  const [activeStep, setActiveStep] = useState(0);

  const active = STEPS[activeStep];
  const ActiveIcon = active.icon;

  return (
    <section
      id="how-it-works"
      className="relative overflow-hidden bg-slate-50 py-20 sm:py-24 lg:py-28"
    >
      {/* Background glow */}
      <div className="pointer-events-none absolute -left-48 top-10 h-[28rem] w-[28rem] rounded-full bg-blue-100/50 blur-[110px]" />

      <div className="pointer-events-none absolute -right-48 bottom-0 h-[30rem] w-[30rem] rounded-full bg-cyan-100/40 blur-[120px]" />

      <div className="relative mx-auto max-w-7xl px-6 lg:px-8">
        {/* Header */}
        <div className="max-w-2xl">
          <div className="inline-flex items-center gap-2.5 rounded-full border border-blue-100 bg-white px-4 py-2 shadow-sm">
            <span className="h-1.5 w-1.5 rounded-full bg-blue-600" />

            <span className="text-[11px] font-semibold uppercase tracking-[0.18em] text-blue-600">
              How it works
            </span>
          </div>

          <h2 className="mt-6 text-4xl font-bold leading-[1.05] tracking-[-0.035em] text-slate-950 sm:text-5xl lg:text-[3.5rem]">
            From profile to{" "}
            <span className="text-blue-600">possibility.</span>
          </h2>

          <p className="mt-5 max-w-xl text-base leading-7 text-slate-600 sm:text-lg sm:leading-8">
            Three simple steps to build your professional sphere, discover
            meaningful connections and grow together.
          </p>
        </div>

        {/* Main */}
        <div className="mt-14 grid items-center gap-12 lg:grid-cols-[0.9fr_1.1fr] lg:gap-16">
          {/* Steps */}
          <div className="relative">
            {/* Vertical connection line */}
            <div className="absolute left-5 top-7 bottom-7 hidden w-px bg-slate-200 sm:block" />

            <div className="space-y-3">
              {STEPS.map((step, index) => {
                const Icon = step.icon;
                const isActive = index === activeStep;

                return (
                  <button
                    key={step.number}
                    type="button"
                    onClick={() => setActiveStep(index)}
                    className={`group relative flex w-full items-start gap-4 rounded-2xl p-4 text-left transition-all duration-300 sm:p-5 ${
                      isActive
                        ? "bg-white shadow-[0_18px_45px_rgba(15,23,42,0.08)] ring-1 ring-blue-100"
                        : "hover:bg-white/70"
                    }`}
                  >
                    {/* Number */}
                    <div
                      className={`relative z-10 flex h-10 w-10 shrink-0 items-center justify-center rounded-xl border text-xs font-bold transition-all duration-300 ${
                        isActive
                          ? "border-blue-600 bg-blue-600 text-white shadow-lg shadow-blue-600/20"
                          : "border-slate-200 bg-white text-slate-400 group-hover:border-blue-200 group-hover:text-blue-500"
                      }`}
                    >
                      {step.number}
                    </div>

                    {/* Content */}
                    <div className="min-w-0 flex-1">
                      <div className="flex items-center gap-2">
                        <Icon
                          size={14}
                          strokeWidth={2}
                          className={
                            isActive ? "text-blue-600" : "text-slate-400"
                          }
                        />

                        <span
                          className={`text-[10px] font-bold uppercase tracking-[0.16em] ${
                            isActive ? "text-blue-600" : "text-slate-400"
                          }`}
                        >
                          {step.label}
                        </span>
                      </div>

                      <h3
                        className={`mt-1.5 text-lg font-semibold ${
                          isActive ? "text-slate-950" : "text-slate-600"
                        }`}
                      >
                        {step.title}
                      </h3>

                      <p
                        className={`mt-1.5 max-w-lg text-sm leading-6 ${
                          isActive ? "text-slate-500" : "text-slate-400"
                        }`}
                      >
                        {step.description}
                      </p>
                    </div>

                    {/* Arrow */}
                    <div
                      className={`mt-2 flex h-8 w-8 shrink-0 items-center justify-center rounded-full transition-all duration-300 ${
                        isActive
                          ? "bg-blue-50 text-blue-600"
                          : "bg-slate-50 text-slate-300 group-hover:bg-blue-50 group-hover:text-blue-500"
                      }`}
                    >
                      <ArrowRight size={15} />
                    </div>
                  </button>
                );
              })}
            </div>
          </div>

          {/* Visual */}
          <div className="relative flex min-h-[430px] items-center justify-center sm:min-h-[520px]">
            {/* Ambient glow */}
            <div className="absolute left-1/2 top-1/2 h-72 w-72 -translate-x-1/2 -translate-y-1/2 rounded-full bg-blue-400/20 blur-[90px] sm:h-96 sm:w-96" />

            {/* Large subtle orbit */}
            <div className="absolute left-1/2 top-1/2 h-[330px] w-[330px] -translate-x-1/2 -translate-y-1/2 rounded-full border border-blue-200/50 sm:h-[450px] sm:w-[450px]" />

            <div className="absolute left-1/2 top-1/2 h-[410px] w-[220px] -translate-x-1/2 -translate-y-1/2 rotate-[25deg] rounded-[50%] border border-cyan-200/40 sm:h-[510px] sm:w-[280px]" />

            {/* Orbit nodes */}
            <span className="absolute left-[13%] top-[28%] h-2 w-2 rounded-full bg-blue-500 shadow-[0_0_18px_rgba(59,130,246,0.8)]" />

            <span className="absolute right-[14%] top-[25%] h-1.5 w-1.5 rounded-full bg-cyan-400 shadow-[0_0_15px_rgba(34,211,238,0.8)]" />

            <span className="absolute bottom-[22%] right-[18%] h-2 w-2 rounded-full bg-blue-500 shadow-[0_0_18px_rgba(59,130,246,0.8)]" />

            {/* Image */}
            <div
              key={active.number}
              className="relative z-10 flex w-[320px] items-center justify-center transition-all duration-500 sm:w-[410px] lg:w-[470px]"
            >
              <img
                src={active.image}
                alt={active.title}
                className="h-auto w-full object-contain drop-shadow-[0_25px_45px_rgba(37,99,235,0.20)]"
              />
            </div>

            {/* Active step indicator */}
            <div className="absolute bottom-3 left-1/2 z-20 -translate-x-1/2">
              <div className="flex items-center gap-2 rounded-full border border-slate-200 bg-white/90 px-3 py-2 shadow-lg shadow-slate-900/5 backdrop-blur-md">
                {STEPS.map((step, index) => (
                  <button
                    key={step.number}
                    type="button"
                    onClick={() => setActiveStep(index)}
                    aria-label={`Show step ${index + 1}`}
                    className={`h-1.5 rounded-full transition-all duration-300 ${
                      index === activeStep
                        ? "w-8 bg-blue-600"
                        : "w-1.5 bg-slate-300 hover:bg-slate-400"
                    }`}
                  />
                ))}
              </div>
            </div>

            {/* Small floating label */}
            <div className="absolute right-0 top-[12%] z-20 hidden rounded-xl border border-blue-100 bg-white/90 px-3 py-2 shadow-lg shadow-slate-900/5 backdrop-blur-md sm:block">
              <div className="flex items-center gap-2">
                <span className="flex h-7 w-7 items-center justify-center rounded-lg bg-blue-50 text-blue-600">
                  <ActiveIcon size={14} />
                </span>

                <div>
                  <p className="text-[9px] font-bold uppercase tracking-[0.14em] text-blue-600">
                    Step {active.number}
                  </p>

                  <p className="text-xs font-semibold text-slate-900">
                    {active.label}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Bottom highlights */}
        <div className="mt-10 grid border-y border-slate-200 sm:grid-cols-3">
          <Highlight
            number="01"
            title="Get started fast"
            text="Set up your professional identity in minutes."
          />

          <Highlight
            number="02"
            title="Real connections"
            text="Find people who genuinely matter to your journey."
          />

          <Highlight
            number="03"
            title="Long-term growth"
            text="Turn conversations into collaboration and opportunity."
          />
        </div>

        {/* Closing statement */}
        <div className="mt-10 flex flex-col items-center gap-3 text-center">
          <div className="flex items-center gap-3">
            <span className="hidden h-px w-14 bg-blue-200 sm:block" />

            <p className="text-xs font-bold uppercase tracking-[0.24em] text-blue-600">
              Connect
              <span className="mx-2 text-slate-300">•</span>
              Discover
              <span className="mx-2 text-slate-300">•</span>
              Grow
            </p>

            <span className="hidden h-px w-14 bg-blue-200 sm:block" />
          </div>

          <p className="text-[10px] font-medium uppercase tracking-[0.28em] text-slate-400">
            A brighter professional tomorrow
          </p>
        </div>
      </div>
    </section>
  );
}

function Highlight({
  number,
  title,
  text,
}: {
  number: string;
  title: string;
  text: string;
}) {
  return (
    <div className="flex items-start gap-4 border-b border-slate-200 py-6 last:border-b-0 sm:border-b-0 sm:border-r sm:px-8 sm:first:pl-0 sm:last:border-r-0 sm:last:pr-0">
      <span className="pt-0.5 text-[10px] font-bold tracking-[0.15em] text-blue-500">
        {number}
      </span>

      <div>
        <h3 className="text-sm font-semibold text-slate-950">{title}</h3>

        <p className="mt-1 text-xs leading-5 text-slate-500">{text}</p>
      </div>
    </div>
  );
}

export default HowItWorksSection;