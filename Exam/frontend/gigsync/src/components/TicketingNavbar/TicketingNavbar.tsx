'use client'
import { usePathname } from 'next/navigation';
import React from 'react';
import Link from 'next/link'
import s from "./TicketingNavbar.module.css"

const TicketingNavbar = () => {
    const pathname = usePathname();

    const isActive = (path: string) => pathname === path ? s.active : '';

    return (
        <nav className={s.navbar}>
            <div className={s['navbar-logo']}>
                <Link href="/">🎵 GigSync</Link>
            </div>

            <ul className={s['navbar-links']}>
                <li>
                    <Link href="/ticketing" className={`${s['nav-btn']} ${isActive('/ticketing')}`}>
                        Home
                    </Link>
                </li>
                <li>
                    <Link href="/ticketing/events" className={`${s['nav-btn']} ${isActive('/ticketing/events')}`}>
                        All Events
                    </Link>
                </li>
                <li>
                    <Link href="/ticketing/smart-search" className={`${s['nav-btn']} ${isActive('/ticketing/smart-search')}`}>
                        Smart Search
                    </Link>
                </li>
            </ul>
        </nav>
    );
};

export default TicketingNavbar;