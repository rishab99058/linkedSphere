import { Button } from "@/components/ui/button";

function SocialRegister() {
  return (
    <div className="space-y-4">
      <div className="flex items-center gap-3">
        <div className="h-px flex-1 bg-border" />

        <span className="text-sm text-muted-foreground">
          OR
        </span>

        <div className="h-px flex-1 bg-border" />
      </div>

      <Button
        type="button"
        variant="outline"
        className="w-full"
      >
        <span className="font-semibold">G</span>

        Continue with Google
      </Button>
    </div>
  );
}

export default SocialRegister;