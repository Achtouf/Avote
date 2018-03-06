<div class="body-section body-section__poll" id="polls-section">
    <div class="grid-row button-bar">
        <div class="button-bar-part">
            <label for="search-input-created-polls" class="search-input-icon"><span class="icon-search "></span></label>
            <input type="text" class="special-input search-input" id="search-input-created-polls" data-input="search" placeholder="Recherche" data-search="#created-poll-container">
            <button class="btn btn-neutral" id="filter-published" data-target="#created-poll-container"><span class="icon-publish" title="scrutins publiés"></span></button>
        </div>
        <div class="button-bar-part">
            <button id="load-poll" class="btn btn-info"><span class="icon-loop2"></span></button>
            <button id="delete-poll" class="btn btn-error is-hovered" data-target="#created-poll-container"><span class="icon-bin"></span></button>
            <button id="add-poll" class="btn btn-success is-hovered"><span class="icon-plus"></span></button>
        </div>
    </div>
    <h3>Mes sondages</h3>
    <div id="created-poll-container"></div>
    <form id="pollcreation-form" class="form-cntr is-hidden">
        <h3>Creation d'un scrutin</h3>
        <div class="form-col-cntr">
            <label for="poll-subject">Sujet</label>
            <input type="text" id="poll-subject" name="poll-subject"/>
        </div>

        <div class="form-row-cntr toggle-btn-cntr">
            <span class="toggle-btn" data-target="#add-poll-choice-cntr">
                <i class="slider"></i>
            </span>
            Ajouter des choix
        </div>

        <div class="form-col-cntr is-hidden" id="add-poll-choice-cntr">
            <div class="form-col-cntr">
                <label for="new-poll-choice-1">Choix 1</label>
                <input type="text" id="new-poll-choice-1" name="new-poll-choice-1" data-input="choice"/>
            </div>
            <div class="form-col-cntr">
                <label for="new-poll-choice-2">Choix 2</label>
                <input type="text" id="new-poll-choice-2" name="new-poll-choice-2" data-input="choice"/>
            </div>
        </div>

        <div class="form-col-cntr">
            <input type="submit" class="submit-input align-right" value="Valider"/>
        </div>
    </form>
</div>
