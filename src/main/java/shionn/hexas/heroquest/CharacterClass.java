package shionn.hexas.heroquest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CharacterClass {
	barbarian("Le barbare"), dwarf("Le nain"), elf("L'elf"), magician("Le magicien");

	private final String displayName;
}
