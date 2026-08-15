import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@base-ui/react/button";

function LoginForm() {
  return (
    <form className="space-y-5">
      <div className="space-y-2">
        <Label htmlFor="username">Username</Label>
        <Input
          id="username"
          type="text"
          placeholder="Enter your username"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="password">Password</Label>
        <Input
          id="password"
          type="password"
          placeholder="Enter your password"
        />
      </div>

      <Button type="submit" className="w-full">
        Login
      </Button>
    </form>
  );
}

export default LoginForm;