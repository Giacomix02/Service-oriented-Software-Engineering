export interface Artist {
    id: number;
    name: string;
    description: string;
}

export interface Song {
    id: number;
    title: string;
    description: string;
    plays: number;
    artist: Artist;
}