package com.hbsis.traqtcamila.model;

import com.hbsis.traqtcamila.R;

/**
 * Representa as categorias de uma atividade.
 */
public enum Category {
    Fitness,
    Concentration,
    Relaxation,
    Performance;
    public int getIconResourceId() {
        switch (this) {
            case Fitness:
                return R.drawable.ic_category_fitness;
            case Concentration:
                return R.drawable.ic_category_concentration;
            case Relaxation:
                return R.drawable.ic_category_relaxation;
            case Performance:
                return R.drawable.ic_category_performance;
        }
        return 0;
    }
}
