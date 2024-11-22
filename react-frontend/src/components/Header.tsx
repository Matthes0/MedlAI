import { Logo } from "./Logo.tsx";

export const Header = () => {
  return (
    <header className="py-6">
      <div className="flex items-center">
        <Logo />
      </div>
    </header>
  );
};
