'use client'
import { usePathname } from 'next/navigation';
import React, { useState } from 'react';
import Link from 'next/link'
import s from "./AnalyzerNavbar.module.css"

const Navbar = () => {
    const pathname = usePathname();
    const [openDropdown, setOpenDropdown] = useState<string | null>(null);

    const isActive = (path: string) => pathname === path ? s.active : '';

    const toggleDropdown = (menu: string) => {
        setOpenDropdown(openDropdown === menu ? null : menu);
    };

    return (
        <nav className={s.navbar}>
            <div className={s['navbar-logo']}>
                <Link href="/">🎵 GigSync</Link>
            </div>

            <ul className={s['navbar-links']}>
                {/* --- SEZIONE ANALYZE --- */}

                {/* Dropdown Canzoni */}
                <li className={s.dropdown} onMouseLeave={() => setOpenDropdown(null)}>
                    <button
                        className={`${s['dropdown-trigger']} ${isActive('/analyze/songs')}`}
                        onClick={() => toggleDropdown('songs')}
                    >
                        Songs ▾
                    </button>
                    <ul className={`${s['dropdown-menu']} ${openDropdown === 'songs' ? s.show : ''}`}>
                        <li><Link href="/analyzer/songs/allSongs">All Songs</Link></li>
                        <li><Link href="/analyzer/songs/searchById">Search by ID</Link></li>
                        <li><Link href="/analyzer/songs/searchByName">Search by Name</Link></li>
                        <li><Link href="/analyzer/songs/searchByArtist">Search by Artist</Link></li>
                    </ul>
                </li>

                {/* Dropdown Artisti */}
                <li className={s.dropdown} onMouseLeave={() => setOpenDropdown(null)}>
                    <button
                        className={`${s['dropdown-trigger']} ${isActive('/analyze/artists')}`}
                        onClick={() => toggleDropdown('artists')}
                    >
                        Artists ▾
                    </button>
                    <ul className={`${s['dropdown-menu']} ${openDropdown === 'artists' ? s.show : ''}`}>
                        <li><Link href="/analyzer/artists/allArtists">All Artists</Link></li>
                        <li><Link href="/analyzer/artists/searchById">Search by ID</Link></li>
                    </ul>
                </li>

                {/* Link Diretto Servizi Streaming */}
                <li>
                    <Link href="/analyzer/streamingServices" className={`${s['nav-btn']} ${isActive('/analyze/streaming-services')}`}>
                        Streaming Services
                    </Link>
                </li>


                {/* --- SEZIONE STATS --- */}

                {/* Dropdown Statistiche Canzoni */}
                <li className={s.dropdown} onMouseLeave={() => setOpenDropdown(null)}>
                    <button
                        className={`${s['dropdown-trigger']} ${s['stats-trigger']} ${isActive('/stats/songs')}`}
                        onClick={() => toggleDropdown('statsSongs')}
                    >
                        Stats Songs ▾
                    </button>
                    <ul className={`${s['dropdown-menu']} ${openDropdown === 'statsSongs' ? s.show : ''}`}>
                        <li><Link href="/analyzer/statsSongs/allStatsSongs">All Song Stats</Link></li>
                        <li><Link href="/analyzer/statsSongs/searchStatsById">Stats by ID</Link></li>
                        <li><Link href="/analyzer/statsSongs/searchStatsByName">Stats by Name</Link></li>
                        <li><Link href="/analyzer/statsSongs/searchStatsByArtist">Stats by Artist</Link></li>
                    </ul>
                </li>

                {/* Dropdown Statistiche Artisti */}
                <li className={s.dropdown} onMouseLeave={() => setOpenDropdown(null)}>
                    <button
                        className={`${s['dropdown-trigger']} ${s['stats-trigger']} ${isActive('/stats/artists')}`}
                        onClick={() => toggleDropdown('statsArtists')}
                    >
                        Stats Artists ▾
                    </button>
                    <ul className={`${s['dropdown-menu']} ${openDropdown === 'statsArtists' ? s.show : ''}`}>
                        <li><Link href="/analyzer/statsArtists/allStatsArtists">All Artist Stats</Link></li>
                        <li><Link href="/analyzer/statsArtists/searchStatsById">Stats by ID</Link></li>
                        <li><Link href="/analyzer/statsArtists/searchStatsByName">Stats by Name</Link></li>
                    </ul>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;