import { Button } from "@/components/ui/button";

function SocialLogin() {
  return (
    <div className="space-y-4">
      <div className="flex items-center gap-3">
        <div className="h-px flex-1 bg-border" />

        <span className="text-xs font-medium text-muted-foreground">
          OR
        </span>

        <div className="h-px flex-1 bg-border" />
      </div>

      <Button
        type="button"
        variant="outline"
        className="h-11 w-full"
      >
        <span className="mr-2 text-base font-bold">G</span>
        Continue with Google
      </Button>
    </div>
  );
}

export default SocialLogin;