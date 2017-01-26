package cl.enexo.dearsoccer.views.createMatch;

import cl.enexo.dearsoccer.models.Match;

/**
 * Created by Kevin on 25-01-2017.
 */

public interface CallBackDataIntent {
    void ok(Match match);
    void fail();
}
