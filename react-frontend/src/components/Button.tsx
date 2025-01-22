import React from "react";
import { ClipLoader } from "react-spinners";

interface ButtonProps {
  variant: "primary" | "secondary";
  children: React.ReactNode;
  onClick?: () => void;
  className?: string;
  isLoading?: boolean;
  disabled?: boolean;
  size?: "sm" | "md" | "lg";
}

const Button: React.FC<ButtonProps> = ({
  variant,
  children,
  onClick,
  isLoading,
  className,
  disabled,
  size = "md",
}) => {
  const sizeStyles = {
    sm: "h-8 px-4 text-sm",
    md: "h-12 px-6 text-base",
    lg: "h-16 px-8 text-lg",
  };

  const baseStyles = `
    relative
    inline-flex
    items-center
    justify-center
    font-medium
    rounded-lg
    transition-all
    duration-200
    ease-in-out
    focus:outline-none
    focus:ring-2
    focus:ring-offset-2
    disabled:opacity-60
    disabled:cursor-not-allowed
    ${sizeStyles[size]}
  `;

  const variantStyles = {
    primary: `
      bg-blue-600
      text-white
      hover:bg-blue-700
      active:bg-blue-800
      focus:ring-blue-500
      disabled:hover:bg-blue-600
    `,
    secondary: `
      bg-gray-200
      text-gray-700
      hover:bg-gray-300
      active:bg-gray-400
      focus:ring-gray-500
      disabled:hover:bg-gray-200
    `,
  };

  return (
    <button
      onClick={onClick}
      disabled={disabled || isLoading}
      className={`
        ${baseStyles}
        ${variantStyles[variant]}
        ${className || ""}
      `}
    >
      {isLoading ? (
        <ClipLoader
          color={variant === "primary" ? "#ffffff" : "#374151"}
          loading={true}
          size={size === "sm" ? 16 : size === "md" ? 24 : 32}
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
