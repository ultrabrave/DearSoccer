package cl.enexo.dearsoccer.views.InformationMatch;

import cl.enexo.dearsoccer.models.Match;

/**
 * Created by Kevin on 30-01-2017.
 */

public interface CallbackValidationParticipe {
    void toParticipe(Match match,String[] ids);
    void inMatch();
}
