import React from "react";
import {} from "react-loaders";
import { ClipLoader } from "react-spinners";
interface ButtonProps {
  variant: "primary" | "secondary";
  children: React.ReactNode;
  onClick?: () => void;
  className?: string;
  isLoading?: boolean;
}

const Button: React.FC<ButtonProps> = ({
  variant,
  children,
  onClick,
  isLoading,
  className,
}) => {
  const baseStyles =
    "w-96 h-16 rounded-2xl font-semibold text-2xl leading-loose flex items-center justify-center transition-all duration-300";

  const variantStyles = {
    primary:
      "bg-blue-600 text-white hover:shadow-[0_0_20px_rgba(108,99,255,0.6)]",
    secondary:
      "bg-gray-300 text-black hover:shadow-[0_0_20px_rgba(217,217,217,0.6)]",
  };

  return (
    <button
      onClick={onClick}
      className={`
        ${baseStyles}
        ${variantStyles[variant]}
        ${className || ""}
      `}
    >
      {isLoading ? (
        <ClipLoader
          color={"bg-grey-300"}
          loading={true}
          size={35}
          aria-label="Loading Spinner"
          data-testid="loader"
        />
      ) : (
        children
      )}
    </button>
  );
};

export default Button;
