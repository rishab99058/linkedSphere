import {
  Card,
  CardContent,
} from "@/components/ui/card";

import { Button } from "@/components/ui/button";
// import { Textarea } from "@/components/ui/textarea";

function CreatePost() {
  return (
    <Card>
      <CardContent className="space-y-4 p-5">
        {/* <Textarea
          placeholder="Share something with your network..."
          className="min-h-28 resize-none"
        /> */}

        <div className="flex justify-end">
          <Button>
            Post
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}

export default CreatePost;