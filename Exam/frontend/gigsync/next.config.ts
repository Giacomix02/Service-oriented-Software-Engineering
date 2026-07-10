import type { NextConfig } from "next";

const nextConfig: NextConfig = {

  /* config options here */
  async rewrites() {
    return [
      {
        // Quando chiami /api-gateway/api/stats, Next.js lo reindirizzerà a Spring
        source: '/api-gateway/:path*',
        destination: 'http://localhost:9000/:path*', 
      },
    ];
  },
  reactCompiler: true,
};

export default nextConfig;
