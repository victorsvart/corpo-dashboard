import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router";
import Alert from "../components/Alert";

export default function Home() {
  const location = useLocation();
  const navigate = useNavigate();
  const [alertOpen, setAlertOpen] = useState(false);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    if (params.get("rsr") === "true") {
      setAlertOpen(true);
      params.delete("rsr");
      navigate({ search: params.toString() }, { replace: true });
    }
  }, [location, navigate]);

  return (
    <div className="min-h-screen text-white py-12 px-6">
      <h1>Home</h1>
      <Alert
        isOpen={alertOpen}
        onClose={() => setAlertOpen(false)}
        message="Welcome!"
        type="success"
      />
    </div>
  );
}
