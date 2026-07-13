import type { NextConfig } from "next";

const nextConfig: NextConfig = {

  /* config options here */
  async rewrites() {

    const gatewayUrl = process.env.API_GATEWAY_URL || 'localhost';
    const gatewayPort= process.env.API_GATEWAY_PORT || '9000';

    return [
      {
        // Quando chiami /api-gateway/api/stats, Next.js lo reindirizzerà a Spring
        source: '/api-gateway/:path*',
        destination: 'http://'+gatewayUrl+':'+gatewayPort+'/:path*',
      },
    ];
  },
  reactCompiler: true,
};

export default nextConfig;
