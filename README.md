en tee mitää... glhf


```mermaid
flowchart LR
    A[Saapuminen] -->|Jono| B[Pöytiinohjaus]
    B --> C[Pöydän ruokatilaus]
    C -->|Tilaukset| K[Keittiö: Tilauksen vastaanotto]
    K --> L[Keittiö: Ruoan valmistus]
    L --> F[Tarjoilu]
    F --> G[Laskutus ja maksaminen]
    G --> H[Poistuminen]
    subgraph KEITTIÖ
        K
        L
    end
```